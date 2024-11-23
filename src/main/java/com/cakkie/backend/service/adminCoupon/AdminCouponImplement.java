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
        if (coupon.getStartDate() != null && coupon.getEndDate() != null) {
//            System.out.println("check start date: " +  coupon.getStartDate());
//            System.out.println("check end date: " + coupon.getStartDate());
            if (coupon.getEndDate().before(coupon.getStartDate())) {
                throw new IllegalArgumentException("End date must be after start date.");
            }
        }
        return couponRepository.save(coupon);
    }


    @Override
    public coupons getCouponById(int id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new AdminCouponNotFound("Sorry, no coupons found with id: " + id));
    }

    @Override
    public coupons updateCoupon(int id, coupons coupon) {
        coupons existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new AdminCouponNotFound("No coupon found with id: " + id));

        existingCoupon.setCode(coupon.getCode());
        existingCoupon.setName(coupon.getName());
        existingCoupon.setQuantity(coupon.getQuantity());
        existingCoupon.setPriceDiscount(coupon.getPriceDiscount());
        existingCoupon.setStartDate(coupon.getStartDate());
        existingCoupon.setEndDate(coupon.getEndDate());

        if (existingCoupon.getStartDate() != null && existingCoupon.getEndDate() != null) {
            if (existingCoupon.getEndDate().before(existingCoupon.getStartDate())) {
                throw new IllegalArgumentException("End date must be after start date.");
            }
        }

        return couponRepository.save(existingCoupon);
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
