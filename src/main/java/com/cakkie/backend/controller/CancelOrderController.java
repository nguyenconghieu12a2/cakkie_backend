package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CancelOrderDTO;
import com.cakkie.backend.service.CancelOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
