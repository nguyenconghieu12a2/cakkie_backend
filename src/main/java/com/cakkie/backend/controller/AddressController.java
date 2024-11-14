package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AddressBody;
import com.cakkie.backend.dto.AddressResponse;
import com.cakkie.backend.dto.DistrictBody;
import com.cakkie.backend.dto.WardBody;
import com.cakkie.backend.model.districts;
import com.cakkie.backend.model.provinces;
import com.cakkie.backend.model.userAddress;
import com.cakkie.backend.model.wards;
import com.cakkie.backend.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/provinces")
    public List<provinces> getAllProvinces() {
        return addressService.getAllProvinces();
    }

    @PostMapping("/districts")
    public List<districts> getDistrictsByProvinceCode(@RequestBody DistrictBody districtBody) {
        return addressService.getDistrictsByProvinceCode(districtBody.getProvinceCode());
    }

    // Change to @PostMapping and keep @RequestBody
    @PostMapping("/wards")
    public List<wards> getWardsByDistrictCode(@RequestBody WardBody wardBody) {
        return addressService.getWardsByDistrictCode(wardBody.getDistrictCode());
    }

    @GetMapping("/user/{userId}/addresses")
    public List<AddressResponse> getUserAddresses(@PathVariable int userId) {
        return addressService.getUserAddresses(userId);
    }

    @PostMapping("/user/{userId}/address/add")
    public userAddress addUserAddress(@PathVariable int userId, @RequestBody AddressBody addressBody) {
        return addressService.addUserAddress(userId, addressBody);
    }

    @PutMapping("/user/{userId}/address/{addressId}/set-default")
    public userAddress setDefaultAddress(@PathVariable int userId, @PathVariable int addressId) {
        return addressService.setDefaultAddress(userId, addressId);
    }

    @PutMapping("/user/{userId}/address/{addressId}/update")
    public userAddress updateUserAddress(
            @PathVariable int userId,
            @PathVariable int addressId,
            @RequestBody AddressBody addressBody) {
        return addressService.updateUserAddress(userId, addressId, addressBody);
    }

    @DeleteMapping("/user/{userId}/address/{addressId}/delete")
    public ResponseEntity<Void> deleteAddress(@PathVariable int userId, @PathVariable int addressId) {
        addressService.deleteUserAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
