package com.cakkie.backend.repository;

import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAddProductSizeRepo extends JpaRepository<productItem, Integer> {
}
