package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.shopOrder;
import com.cakkie.backend.model.shoppingCartItem;
import com.cakkie.backend.service.CartService;
import com.cakkie.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final OrderService orderService;


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
