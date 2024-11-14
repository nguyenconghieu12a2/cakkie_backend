package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CancelOrderDTO;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.service.CancelOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CancelOrderController {
    private final CancelOrderService cancelOrderService;

    @GetMapping("/api/cacel-order")
    public ResponseEntity<List<CancelOrderDTO>> cancelOrder() {
        List<CancelOrderDTO> cancelOrder = cancelOrderService.getAllCanceledOrder();
        return new ResponseEntity<>(cancelOrder, HttpStatus.OK);
    }

    @GetMapping("/api/cancel-order/detail/{userId}")
    public ResponseEntity<List<CancelOrderDTO>> detailCancelOrderByUserId(@PathVariable int userId) {
        List<CancelOrderDTO> detailCancelOrders = cancelOrderService.getDetailCancelOrderByUserId(userId);
        return new ResponseEntity<>(detailCancelOrders, HttpStatus.OK);
    }

    @GetMapping("/api/cancel-order/product-detail/{userId}")
    public ResponseEntity<List<CancelOrderDTO>> getProductCancelDetailByUserId(@PathVariable int userId) {
        List<CancelOrderDTO> productCancelDetails = cancelOrderService.getDetailProductCancelByUserId(userId);
        return new ResponseEntity<>(productCancelDetails, HttpStatus.OK);
    }

        @PostMapping("/api/ban-user/{userId}")
    public ResponseEntity<String> banUser(
            @PathVariable int userId,
            @RequestBody String bannedReason) {

        Optional<userSite> user = cancelOrderService.findUserById(userId);

        if (user.isPresent()) {
            cancelOrderService.banUser(userId, bannedReason);
            return new ResponseEntity<>("User banned successfully with reason: " + bannedReason, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

}
