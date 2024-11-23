package com.cakkie.backend.repository.adminDiscount;

import com.cakkie.backend.model.category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCategoryDiscountRepo extends JpaRepository<category, Integer> {
}
