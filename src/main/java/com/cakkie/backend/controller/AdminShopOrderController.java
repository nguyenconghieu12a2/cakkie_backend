package com.cakkie.backend.controller;

import com.cakkie.backend.dto.OrderStatusDTO;
import com.cakkie.backend.model.orderStatus;
import com.cakkie.backend.service.AdminShopOrderService;
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
        int statusId = requestBody.get("statusId");
        adminShopOrderService.updateOrderStatus(id, statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/order/statuses")
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses() {
        List<OrderStatusDTO> statuses = adminShopOrderService.getAllOrderStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @GetMapping("/api/order/{id}/status")
    public ResponseEntity<String> getCurrentOrderStatus(@PathVariable int id) {
        String currentStatus = adminShopOrderService.getCurrentOrderStatusName(id);
        if (currentStatus != null) {
            return new ResponseEntity<>(currentStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
