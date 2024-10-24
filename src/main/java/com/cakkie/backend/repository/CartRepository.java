package com.cakkie.backend.repository;

import com.cakkie.backend.model.productCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository {
    // Query to get all records
    @Query(value = "SELECT e FROM productCart e")
    List<productCart> getAllRecords();

    // Query to get records by userId
    @Query(value = "SELECT e FROM productCart e WHERE e.userId.id = :userId")
    List<productCart> getRecordsByUserId(@Param("userId") int userId);

    // Query to get records by productId
    @Query(value = "SELECT e FROM productCart e WHERE e.productId.id = :productId")
    List<productCart> getRecordsByProductId(@Param("productId") int productId);

    // Query to get records that are not deleted
    @Query(value = "SELECT e FROM productCart e WHERE e.isDeleted = 0")
    List<productCart> getActiveRecords();
}
