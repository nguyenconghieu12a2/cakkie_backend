package com.cakkie.backend.service;
import com.cakkie.backend.DTO.CouponDTO;
import com.cakkie.backend.model.coupons;

import java.util.List;

public interface CouponService {
    List<CouponDTO> getAllCoupons();
    coupons addCoupon(coupons coupon);
    coupons getCouponById(int id);
    coupons updateCoupon(coupons coupon, int id);
    void deleteCoupon(int id);
}
