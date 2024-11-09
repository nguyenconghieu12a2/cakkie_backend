package com.cakkie.backend.controller;

import com.cakkie.backend.model.coupons;
import com.cakkie.backend.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CouponController {
    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupons/getall")
    public ResponseEntity<List<coupons>> getDeletedCoupons() {
        List<coupons> allCoupons = couponService.getAllCoupons();
        return ResponseEntity.ok(allCoupons);
    }
}
