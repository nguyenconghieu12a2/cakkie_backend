package com.cakkie.backend.repository;

import com.cakkie.backend.model.coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<coupons, Integer> {
}
