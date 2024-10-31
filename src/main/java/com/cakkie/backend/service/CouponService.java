package com.cakkie.backend.service;
import com.cakkie.backend.dto.CouponDTO;
import com.cakkie.backend.model.coupons;

import java.util.List;

public interface CouponService {
    List<CouponDTO> getAllCoupons();
    coupons addCoupon(coupons coupon);
    coupons getCouponById(int id);
    coupons updateCoupon(int id, coupons coupon);
    void deleteCoupon(int id);
}
