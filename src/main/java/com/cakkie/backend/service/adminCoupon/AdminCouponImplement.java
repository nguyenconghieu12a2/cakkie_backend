package com.cakkie.backend.service.adminCoupon;

import com.cakkie.backend.dto.adminCoupon.AdminCouponDTO;
import com.cakkie.backend.exception.adminCoupon.AdminCouponNotFound;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.repository.adminCoupon.AdminCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponImplement implements AdminCouponService {

    @Autowired
    private AdminCouponRepository couponRepository;

    @Override
    public List<AdminCouponDTO> getAllCoupons() {
        return couponRepository.getAllCoupons();
    }

    @Override
    public coupons addCoupon(coupons coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public coupons getCouponById(int id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new AdminCouponNotFound("Sorry, no coupons found with id: " + id));
    }

    @Override
    public coupons updateCoupon(int id, coupons coupon) {
        coupons existCoupons = couponRepository.findById(id)
                .orElseThrow(() -> new AdminCouponNotFound("Sorry, no coupons found with id: " + id));
        existCoupons.setCode(coupon.getCode());
        existCoupons.setName(coupon.getName());
        existCoupons.setQuantity(coupon.getQuantity());
        existCoupons.setPriceDiscount(coupon.getPriceDiscount());
        existCoupons.setStartDate(coupon.getStartDate());
        existCoupons.setEndDate(coupon.getEndDate());
        return couponRepository.save(existCoupons);
    }

    @Override
    public void deleteCoupon(int id) {
        coupons coupon = couponRepository.findById(id)
                .orElseThrow(() -> new AdminCouponNotFound("Sorry, no coupon found with this id: " + id));

        // Soft delete: set isDeleted to 1
        coupon.setIsDeleted(0);
        couponRepository.save(coupon);
    }
}
