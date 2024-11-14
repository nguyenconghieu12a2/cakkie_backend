package com.cakkie.backend.service;

import com.cakkie.backend.model.coupons;
import com.cakkie.backend.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {
    private CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<coupons> getAllCoupons() {
        return couponRepository.findByIsDeleted(1);
    }
}
