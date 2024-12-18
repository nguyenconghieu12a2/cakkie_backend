package com.cakkie.backend.service.adminCoupon;
import com.cakkie.backend.dto.adminCoupon.AdminCouponDTO;
import com.cakkie.backend.model.coupons;

import java.util.List;

public interface AdminCouponService {
    List<AdminCouponDTO> getAllCoupons();
    coupons addCoupon(coupons coupon);
    coupons getCouponById(int id);
    coupons updateCoupon(int id, coupons coupon);
    void deleteCoupon(int id);
}
