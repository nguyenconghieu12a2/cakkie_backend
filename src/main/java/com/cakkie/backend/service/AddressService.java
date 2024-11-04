package com.cakkie.backend.service;

import com.cakkie.backend.dto.AddressBody;
import com.cakkie.backend.dto.AddressResponse;
import com.cakkie.backend.model.*;
import com.cakkie.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private ProvinceRepository provinceRepository;
    private DistrictRepository districtRepository;
    private WardRepository wardRepository;
    private UserAddressRepository userAddressRepository;
    private UserSiteRepository userSiteRepository;
    private AddressRepository addressRepository;

    public AddressService(ProvinceRepository provinceRepository, DistrictRepository districtRepository, WardRepository wardRepository, UserAddressRepository userAddressRepository, UserSiteRepository userSiteRepository, AddressRepository addressRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
        this.userAddressRepository = userAddressRepository;
        this.userSiteRepository = userSiteRepository;
        this.addressRepository = addressRepository;
    }

    // Fetch all provinces
    public List<provinces> getAllProvinces() {
        return (List<provinces>) provinceRepository.findAll();
    }

    // Fetch all districts by province code
    public List<districts> getDistrictsByProvinceCode(String provinceCode) {
        Optional<provinces> province = provinceRepository.findById(provinceCode);
        return province.map(districtRepository::findByProvinceCode).orElse(null);
    }

    // Fetch all wards by district code
    public List<wards> getWardsByDistrictCode(String districtCode) {
        Optional<districts> district = districtRepository.findById(districtCode);
        return district.map(wardRepository::findByDistrictCode).orElse(null);
    }

    public List<AddressResponse> getUserAddresses(int userId) {
        List<userAddress> addresses = userAddressRepository.findByUserId_Id(userId);
        return addresses.stream()
                .filter(userAddress -> userAddress.getIsDeleted() == 1)
                .map(userAddress -> {
                    districts district = userAddress.getAddressId().getDistrictsCode();
                    wards ward = userAddress.getAddressId().getWardsCode();
                    provinces province = district.getProvinceCode();

                    return new AddressResponse(
                            userAddress.getAddressId().getId(),
                            userAddress.getAddressId().getRecievedName(),
                            userAddress.getAddressId().getDetailAddress(),
                            province.getCode(),  // Set provinceCode here
                            province.getFullNameEn(),
                            district.getFullNameEn(),
                            ward.getFullNameEn(),
                            userAddress.getUserId().getPhone(),
                            userAddress.getIsDefault() == 1
                    );
                }).collect(Collectors.toList());
    }

    @Transactional
    public userAddress addUserAddress(int userId, AddressBody addressBody) {
        // Retrieve the user by ID
        userSite user = userSiteRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve districts and wards by code
        districts district = districtRepository.findById(addressBody.getDistrictsCode())
                .orElseThrow(() -> new RuntimeException("District not found"));
        wards ward = wardRepository.findById(addressBody.getWardsCode())
                .orElseThrow(() -> new RuntimeException("Ward not found"));

        // Create a new address entity and set fields from DTO
        address newAddress = new address();
        newAddress.setRecievedName(addressBody.getRecievedName());
        newAddress.setDetailAddress(addressBody.getDetailAddress());
        newAddress.setDistrictsCode(district);
        newAddress.setWardsCode(ward);
        newAddress.setIsDeleted(1);

        // Save the new address entity
        address savedAddress = addressRepository.save(newAddress);

        // Check if there are any existing addresses set as default
        List<userAddress> existingDefaultAddresses = userAddressRepository.findByUserId_IdAndIsDefault(userId, 1);

        // If this is the first address or if no other default addresses exist, set it as default
        boolean shouldSetAsDefault = addressBody.isDefault() || existingDefaultAddresses.isEmpty();

        if (shouldSetAsDefault) {
            // Remove default status from any existing default addresses
            for (userAddress existingAddress : existingDefaultAddresses) {
                existingAddress.setIsDefault(0);
                userAddressRepository.save(existingAddress);  // Persist each update
                System.out.println("Removed default from address ID: " + existingAddress.getAddressId().getId());
            }
        }

        // Create a new userAddress entry with the saved address
        userAddress userAddress = new userAddress();
        userAddress.setUserId(user);
        userAddress.setAddressId(savedAddress);
        userAddress.setIsDefault(shouldSetAsDefault ? 1 : 0);
        userAddress.setIsDeleted(1);

        // Save and return the new userAddress
        userAddress savedUserAddress = userAddressRepository.save(userAddress);
        System.out.println("New default set: " + (savedUserAddress.getIsDefault() == 1));

        return savedUserAddress;
    }

    @Transactional
    public userAddress setDefaultAddress(int userId, int addressId) {
        // Unset any existing default address for the user
        List<userAddress> existingDefaultAddresses = userAddressRepository.findByUserId_IdAndIsDefault(userId, 1);
        for (userAddress existingAddress : existingDefaultAddresses) {
            existingAddress.setIsDefault(0);
            userAddressRepository.save(existingAddress);  // Persist each update
        }

        // Set the specified address as the default
        userAddress userAddress = userAddressRepository.findByUserId_IdAndAddressId_Id(userId, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found for the user"));
        userAddress.setIsDefault(1);

        // Save and return the updated user address
        return userAddressRepository.save(userAddress);
    }

    @Transactional
    public userAddress updateUserAddress(int userId, int addressId, AddressBody addressBody) {
        // Retrieve the address entry by userId and addressId
        userAddress userAddress = userAddressRepository.findByUserId_IdAndAddressId_Id(userId, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found for the user"));

        // Retrieve districts and wards by code
        districts district = districtRepository.findById(addressBody.getDistrictsCode())
                .orElseThrow(() -> new RuntimeException("District not found"));
        wards ward = wardRepository.findById(addressBody.getWardsCode())
                .orElseThrow(() -> new RuntimeException("Ward not found"));

        // Update fields in the address entity
        address addressToUpdate = userAddress.getAddressId();
        addressToUpdate.setRecievedName(addressBody.getRecievedName());
        addressToUpdate.setDetailAddress(addressBody.getDetailAddress());
        addressToUpdate.setDistrictsCode(district);
        addressToUpdate.setWardsCode(ward);
        addressRepository.save(addressToUpdate);  // Save the updated address details

        // If the new address should be default, update all other addresses to remove default status
        if (addressBody.isDefault()) {
            List<userAddress> existingDefaultAddresses = userAddressRepository.findByUserId_IdAndIsDefault(userId, 1);
            for (userAddress existing : existingDefaultAddresses) {
                existing.setIsDefault(0);
                userAddressRepository.save(existing);  // Persist each update
            }
        }

        // Set the isDefault value based on the input
        userAddress.setIsDefault(addressBody.isDefault() ? 1 : 0);
        return userAddressRepository.save(userAddress);  // Save and return the updated userAddress
    }

    @Transactional
    public userAddress deleteUserAddress(int userId, int addressId) {
        // Find the user address entry by userId and addressId
        userAddress userAddress = userAddressRepository.findByUserId_IdAndAddressId_Id(userId, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found for the user"));

        // Mark the address as deleted by setting isDeleted to 0
        userAddress.setIsDeleted(0);
        return userAddressRepository.save(userAddress);
    }
}
