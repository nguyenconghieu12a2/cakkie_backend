package com.cakkie.backend.repository;

import com.cakkie.backend.dto.CouponDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cakkie.backend.model.coupons;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CouponRepository extends JpaRepository<coupons, Integer> {
    @Query(value = "SELECT new com.cakkie.backend.dto.CouponDTO"
            +"(c.id, c.code, c.name, c.quantity, c.priceDiscount, c.startDate, c.endDate, c.isDeleted)"
            +"FROM coupons c WHERE c.isDeleted = 1"
    )
    List<CouponDTO> getAllCoupons();
}
