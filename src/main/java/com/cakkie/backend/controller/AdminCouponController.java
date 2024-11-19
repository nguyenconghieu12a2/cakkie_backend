package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminCouponDTO;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.service.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    private AdminCouponDTO convertToDTO(coupons coupon) {
        AdminCouponDTO dto = new AdminCouponDTO();
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

    private coupons convertToEntity(AdminCouponDTO dto) {
        coupons coupon = new coupons();
        coupon.setId(dto.getId());
        coupon.setCode(dto.getCode());
        coupon.setName(dto.getName());
        coupon.setQuantity(dto.getQuantity());
        coupon.setPriceDiscount(dto.getPriceDiscount());
        coupon.setStartDate(dto.getStartDate());
        coupon.setEndDate(dto.getEndDate());
        coupon.setIsDeleted(dto.getIsDeleted());
        return coupon;
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<AdminCouponDTO>> getAllCoupons() {
        List<AdminCouponDTO> coupons = adminCouponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<AdminCouponDTO> getCouponById(@PathVariable int id) {
        coupons coupon = adminCouponService.getCouponById(id);
        return new ResponseEntity<>(convertToDTO(coupon), HttpStatus.OK);
    }

    @PostMapping("/add-coupons")
    public ResponseEntity<AdminCouponDTO> addCoupon(@RequestBody coupons coupon) {
        coupons savedCoupon = adminCouponService.addCoupon(coupon);
        return new ResponseEntity<>(convertToDTO(savedCoupon), HttpStatus.CREATED);
    }

    @PutMapping("/update-coupons/{id}")
    public ResponseEntity<AdminCouponDTO> updateCoupon(@PathVariable int id, @RequestBody AdminCouponDTO adminCouponDTO) {
        coupons updateCouponEntity = convertToEntity(adminCouponDTO);
        coupons updatedCoupon = adminCouponService.updateCoupon(id, updateCouponEntity);
        return new ResponseEntity<>(convertToDTO(updatedCoupon), HttpStatus.OK);
    }

    @DeleteMapping("/delete-coupons/{id}")
    public ResponseEntity<Void> deleteCouponById(@PathVariable int id) {
        adminCouponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No need to check again
    }
}
