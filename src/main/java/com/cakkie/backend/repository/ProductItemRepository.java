package com.cakkie.backend.repository;

import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<productItem, Integer> {
    Optional<productItem> findByProIdAndSize(product proId, String size);
}
