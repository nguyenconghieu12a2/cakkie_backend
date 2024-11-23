package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.shopOrder;
import com.cakkie.backend.model.orderLine;
import com.cakkie.backend.model.orderStatus;
import com.cakkie.backend.model.shoppingCartItem;
import com.cakkie.backend.repository.OrderStatusRepository;
import com.cakkie.backend.service.CartService;
import com.cakkie.backend.service.OrderService;
import com.cakkie.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final OrderService orderService;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductService productService; // A service to manage products

    @PostMapping(path = "/order/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId,
                                              @RequestBody Map<String, String> requestBody) {
        try {
            String cancelReason = requestBody.get("cancelReason");

            shopOrder order = orderService.getOrderById(orderId);
            if (order == null) {
                return ResponseEntity.status(404).body("Order not found.");
            }

            orderStatus orderStatusId = orderStatusRepository.findById(5).orElseThrow(() -> new RuntimeException("Cannot find orderStatus = 5"));
            order.setOrderStatusId(orderStatusId);
            Date currentDate = new Date();
            order.setCanceledReason(cancelReason);
            order.setCanceledDate(currentDate);
            orderService.updateOrder(order);


            for (orderLine item : order.getOrderLinesList()) {
                productService.restoreProductQuantity(item.getProductItemId(), item.getQty());
            }

            return ResponseEntity.ok("Order canceled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to cancel order. Please try again.");
        }
    }

    @PostMapping(path="addToOrder", consumes = {"application/json"})
    public ResponseEntity<shopOrder> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
//        if (existingItem != null) {
//            int newQuantity = existingItem.getQty() + productCartDTO.getQuantity();
//            existingItem.setQty(newQuantity);
//            shoppingCartItem savedItem = orderService.updateCartItem(existingItem);
//            return ResponseEntity.ok(savedItem);
//        } else {
//            shoppingCartItem savedItem = orderService.addProductCart(productCartDTO);
//            return ResponseEntity.ok(savedItem);
//        }
    }
}
