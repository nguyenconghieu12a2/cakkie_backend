package com.cakkie.backend.repository;

import com.cakkie.backend.dto.AdminCouponDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cakkie.backend.model.coupons;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AdminCouponRepository extends JpaRepository<coupons, Integer> {
    @Query(value = "SELECT new com.cakkie.backend.dto.AdminCouponDTO"
            +"(c.id, c.code, c.name, c.quantity, c.priceDiscount, c.startDate, c.endDate, c.isDeleted)"
            +"FROM coupons c WHERE c.isDeleted = 1"
    )
    List<AdminCouponDTO> getAllCoupons();
}
