package com.cakkie.backend.controller.adminShopOrder;

import com.cakkie.backend.dto.adminOrder.AdminOrderStatusDTO;
import com.cakkie.backend.service.adminShopOrder.AdminShopOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminShopOrderController {
    private final AdminShopOrderService adminShopOrderService;

    @PutMapping("/order/update-status/{id}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable int id, @RequestBody Map<String, Object> requestBody) {
        System.out.println("Received request to update order status with ID: " + id); // Debug log
        int statusId = (int) requestBody.get("statusId");
        String cancelReason = (String) requestBody.get("cancelReason");
        adminShopOrderService.updateOrderStatus(id, statusId, cancelReason);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/statuses")
    public ResponseEntity<List<AdminOrderStatusDTO>> getAllOrderStatuses() {
        List<AdminOrderStatusDTO> statuses = adminShopOrderService.getAllOrderStatuses();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/order/status/{orderId}")
    public ResponseEntity<AdminOrderStatusDTO> getOrderStatusById(@PathVariable int orderId) {
        AdminOrderStatusDTO orderStatus = adminShopOrderService.getOrderStatusById(orderId);
        return ResponseEntity.ok(orderStatus);
    }
}
