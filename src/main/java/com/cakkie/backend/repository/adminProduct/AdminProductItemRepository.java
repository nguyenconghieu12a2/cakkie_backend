package com.cakkie.backend.repository.adminProduct;

import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProductItemRepository extends JpaRepository<productItem, Integer> {
    Optional<productItem> findByProIdAndSize(product proId, String size);
}
