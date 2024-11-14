package com.cakkie.backend.repository;

import com.cakkie.backend.model.userAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends CrudRepository<userAddress, Integer> {
    List<userAddress> findByUserId_Id(int userId);

    int countByUserId_Id(int userId);

    boolean existsByUserId_IdAndIsDeleted(int userId, int isDeleted);

    List<userAddress> findByUserId_IdAndIsDefault(int userId, int isDefault);

    Optional<userAddress> findByUserId_IdAndAddressId_Id(int userId, int addressId);
}
