package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminCancelOrderDTO;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.service.AdminCancelOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminCancelOrderController {
    private final AdminCancelOrderService adminCancelOrderService;

    @GetMapping("/cacel-order")
    public ResponseEntity<List<AdminCancelOrderDTO>> cancelOrder() {
        List<AdminCancelOrderDTO> cancelOrder = adminCancelOrderService.getAllCanceledOrder();
        return new ResponseEntity<>(cancelOrder, HttpStatus.OK);
    }

    @GetMapping("/cancel-order/detail/{userId}")
    public ResponseEntity<List<AdminCancelOrderDTO>> detailCancelOrderByUserId(@PathVariable int userId) {
        List<AdminCancelOrderDTO> detailCancelOrders = adminCancelOrderService.getDetailCancelOrderByUserId(userId);
        return new ResponseEntity<>(detailCancelOrders, HttpStatus.OK);
    }

    @GetMapping("/cancel-order/product-detail/{userId}")
    public ResponseEntity<List<AdminCancelOrderDTO>> getProductCancelDetailByUserId(@PathVariable int userId) {
        List<AdminCancelOrderDTO> productCancelDetails = adminCancelOrderService.getDetailProductCancelByUserId(userId);
        return new ResponseEntity<>(productCancelDetails, HttpStatus.OK);
    }

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<String> banUser(
            @PathVariable int userId,
            @RequestBody String bannedReason) {

        Optional<userSite> user = adminCancelOrderService.findUserById(userId);

        if (user.isPresent()) {
            adminCancelOrderService.banUser(userId, bannedReason);
            return new ResponseEntity<>("User banned successfully with reason: " + bannedReason, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

}