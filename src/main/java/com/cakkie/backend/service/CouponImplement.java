package com.cakkie.backend.service;

import com.cakkie.backend.DTO.CouponDTO;
import com.cakkie.backend.exception.CouponNotFound;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponImplement implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<CouponDTO> getAllCoupons() {
        return couponRepository.getAllCoupons();
    }

    @Override
    public coupons addCoupon(coupons coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public coupons getCouponById(int id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFound("Sorry, no coupons found with id: " + id));
    }

    @Override
    public coupons updateCoupon(coupons coupon, int id) {
        return couponRepository.findById(id).map(existingCoupon -> {
            existingCoupon.setCode(coupon.getCode());
            existingCoupon.setName(coupon.getName());
            existingCoupon.setQuantity(coupon.getQuantity());
            existingCoupon.setPriceDiscount(coupon.getPriceDiscount());
            existingCoupon.setStartDate(coupon.getStartDate());
            existingCoupon.setEndDate(coupon.getEndDate());
            return couponRepository.save(existingCoupon);
        }).orElseThrow(() -> new CouponNotFound("Sorry, this coupon could not be found"));
    }

    @Override
    public void deleteCoupon(int id) {
        coupons coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFound("Sorry, no coupon found with this id: " + id));

        // Soft delete: set isDeleted to 1
        coupon.setIsDeleted(0);
        couponRepository.save(coupon);
    }
}
