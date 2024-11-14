package com.cakkie.backend.controller;

import com.cakkie.backend.dto.OrderStatusDTO;
import com.cakkie.backend.service.AdminShopOrderService;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminShopOrderController {
    private final AdminShopOrderService adminShopOrderService;

    @PutMapping("/api/order/{id}/update-status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable int id, @RequestBody Map<String, Integer> requestBody) {
        System.out.println("Received request to update order status with ID: " + id); // Debug log
        int statusId = requestBody.get("statusId");
        adminShopOrderService.updateOrderStatus(id, statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/order/statuses")
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses() {
        List<OrderStatusDTO> statuses = adminShopOrderService.getAllOrderStatuses();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/api/order/{orderId}/status")
    public ResponseEntity<OrderStatusDTO> getOrderStatusById(@PathVariable int orderId) {
        OrderStatusDTO orderStatus = adminShopOrderService.getOrderStatusById(orderId);
        return ResponseEntity.ok(orderStatus);
    }
}
