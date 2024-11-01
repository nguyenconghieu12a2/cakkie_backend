package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.model.productCart;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.shoppingCartItem;
import com.cakkie.backend.repository.CartRepository;
import com.cakkie.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;

    @PostMapping(path="addToCart", consumes = {"application/json"})
    public ResponseEntity<shoppingCartItem> addToCart(@RequestBody CartDTO productCartDTO) {
        shoppingCartItem existingItem = cartService.findCartItem(productCartDTO.getCartId(), productCartDTO.getProductItemId());

        if (existingItem != null) {
            int newQuantity = existingItem.getQty() + productCartDTO.getQuantity();
            existingItem.setQty(newQuantity);
            shoppingCartItem savedItem = cartService.updateCartItem(existingItem);
            return ResponseEntity.ok(savedItem);
        } else {
            shoppingCartItem savedItem = cartService.addProductCart(productCartDTO);
            return ResponseEntity.ok(savedItem);
        }
    }
    @PostMapping(path = "deleteCartItem", consumes = {"application/json"})
    public void deleteCartItem(@RequestBody CartDTO productCartDTO) {
         cartService.deleteProductCart(productCartDTO.getCartId());
    }
}
