package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface orderLineRepository extends JpaRepository<orderLine, Integer> {
    @Query(value = "SELECT TOP 1 * FROM order_line WHERE product_item_id = :productItemId AND id = :orderLineId ", nativeQuery = true)
    Optional<orderLine> findFirstByProductItemId(@Param("productItemId") int productItemId, @Param("orderLineId") int orderLineId);
}
