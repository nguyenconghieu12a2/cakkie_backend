package com.cakkie.backend.controller;

import com.cakkie.backend.DTO.CouponDTO;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CouponController {

    private final CouponService couponService;

    private CouponDTO convertToDTO(coupons coupon) {
        CouponDTO dto = new CouponDTO();
        dto.setId(coupon.getId());
        dto.setCode(coupon.getCode());
        dto.setName(coupon.getName());  // Corrected to use the proper field
        dto.setQuantity(coupon.getQuantity());
        dto.setPriceDiscount(coupon.getPriceDiscount());
        dto.setStartDate(coupon.getStartDate());
        dto.setEndDate(coupon.getEndDate());
        dto.setIsDeleted(coupon.getIsDeleted());
        return dto;
    }

    @GetMapping("/api/coupons")
    public ResponseEntity<List<CouponDTO>> getAllCoupons() {
        List<CouponDTO> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/api/coupons/{id}")
    public ResponseEntity<CouponDTO> getCouponById(@PathVariable int id) {
        coupons coupon = couponService.getCouponById(id);
        return new ResponseEntity<>(convertToDTO(coupon), HttpStatus.OK);
    }

    @PostMapping("/api/coupons")
    public ResponseEntity<CouponDTO> addCoupon(@RequestBody coupons coupon) {
        coupons savedCoupon = couponService.addCoupon(coupon);
        return new ResponseEntity<>(convertToDTO(savedCoupon), HttpStatus.CREATED);
    }

    @PutMapping("/api/coupons/update/{id}")
    public ResponseEntity<CouponDTO> updateCoupon(@RequestBody coupons coupon, @PathVariable int id) {
        coupons updatedCoupon = couponService.updateCoupon(coupon, id);
        CouponDTO couponDTO = convertToDTO(updatedCoupon);
        return new ResponseEntity<>(couponDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/coupons/delete/{id}")
    public ResponseEntity<Void> deleteCouponById(@PathVariable int id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No need to check again
    }
}
