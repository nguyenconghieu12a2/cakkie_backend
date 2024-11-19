package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminOrderDTO;
import com.cakkie.backend.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    @GetMapping("/order")
    public ResponseEntity<List<AdminOrderDTO>> getAllOrders() {
        List<AdminOrderDTO> orders = adminOrderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/detail-order/{id}")
    public ResponseEntity<AdminOrderDTO> getOrderById(@PathVariable int id) {
        AdminOrderDTO order = adminOrderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
