package com.cakkie.backend;


import com.cakkie.backend.controller.CartController;
import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.shoppingCartItem;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    private shoppingCart mockShoppingCart;
    private userSite mockUser;
    private productItem mockProductItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new userSite();
        mockUser.setId(1);

        mockShoppingCart = new shoppingCart();
        mockShoppingCart.setId(1);
        mockShoppingCart.setUserId(mockUser);
        mockShoppingCart.setShoppingCartItemsList(Collections.emptyList()); // fake the list item

        mockProductItem = new productItem();
        mockProductItem.setId(2);
        mockProductItem.setSize("M");
        mockProductItem.setQtyInStock(100);
        mockProductItem.setPrice(200);
        mockProductItem.setIsDeleted(0);

    }

    @Test
    void testAddNewItemToCart() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(1);

        shoppingCartItem newItem = new shoppingCartItem();
        newItem.setCartId(mockShoppingCart); 
        newItem.setProductItemId(mockProductItem);
        newItem.setQty(1);

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(null);
        when(cartService.addProductCart(any(CartDTO.class))).thenReturn(newItem);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.ok(newItem), response);
        verify(cartService).addProductCart(any(CartDTO.class));
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
    }

    @Test
    void testUpdateExistingItemQuantityInCart() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(3);

        shoppingCartItem existingItem = new shoppingCartItem();
        existingItem.setCartId(mockShoppingCart); 
        existingItem.setProductItemId(mockProductItem);
        existingItem.setQty(2);

        shoppingCartItem updatedItem = new shoppingCartItem();
        updatedItem.setCartId(mockShoppingCart); 
        updatedItem.setProductItemId(mockProductItem);
        updatedItem.setQty(5);  // 2 + 3 = 5

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(existingItem);
        when(cartService.updateCartItem(any(shoppingCartItem.class))).thenReturn(updatedItem);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.ok(updatedItem), response);
        assertEquals(5, response.getBody().getQty());
        verify(cartService).updateCartItem(existingItem);
        verify(cartService, never()).addProductCart(any(CartDTO.class));
    }

    @Test
    void testAddItemWithZeroQuantity() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(0);

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(null);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.badRequest().build(), response);
        verify(cartService, never()).addProductCart(any(CartDTO.class));
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
    }

    @Test
    void testUpdateExistingItemWithZeroQuantity() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(0);

        shoppingCartItem existingItem = new shoppingCartItem();
        existingItem.setCartId(mockShoppingCart); 
        existingItem.setProductItemId(mockProductItem);
        existingItem.setQty(2);

        shoppingCartItem updatedItem = new shoppingCartItem();
        updatedItem.setCartId(mockShoppingCart); 
        updatedItem.setProductItemId(mockProductItem);
        updatedItem.setQty(2);

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(existingItem);
        when(cartService.updateCartItem(any(shoppingCartItem.class))).thenReturn(updatedItem);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.ok(updatedItem), response);
        assertEquals(2, response.getBody().getQty());
        verify(cartService).updateCartItem(existingItem);
        verify(cartService, never()).addProductCart(any(CartDTO.class));
    }
    @Test
    void testAddItemWithQuantityExceedingStock() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(900);

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(null);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.badRequest().build(), response);
        verify(cartService, never()).addProductCart(any(CartDTO.class));
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
    }

    @Test
    void testUpdateExistingItemQuantityExceedingStock() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(900);

        shoppingCartItem existingItem = new shoppingCartItem();
        existingItem.setCartId(mockShoppingCart);
        existingItem.setProductItemId(mockProductItem);
        existingItem.setQty(2);

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(existingItem);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.badRequest().build(), response);
        verify(cartService, never()).updateCartItem(existingItem);
        verify(cartService, never()).addProductCart(any(CartDTO.class));
    }

    @Test
    void testAddToNonExistentCart() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(999);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(3);


        when(cartService.findCartItem(eq(999), eq(2))).thenReturn(null);
        when(cartService.addProductCart(any(CartDTO.class))).thenThrow(new RuntimeException("Cart not found"));

        ResponseEntity<shoppingCartItem> response;
        try {
            response = cartController.addToCart(cartDTO);
        } catch (RuntimeException e) {
            response = ResponseEntity.badRequest().build();
        }

        assertEquals(ResponseEntity.badRequest().build(), response);
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
        verify(cartService).addProductCart(any(CartDTO.class));
    }

    @Test
    void testAddNonExistentProductItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(999);
        cartDTO.setQuantity(3);

        when(cartService.findCartItem(eq(1), eq(999))).thenReturn(null);
        when(cartService.addProductCart(any(CartDTO.class)))
                .thenThrow(new RuntimeException("Product item not found"));

        ResponseEntity<shoppingCartItem> response;
        try {
            response = cartController.addToCart(cartDTO);
        } catch (RuntimeException e) {
            response = ResponseEntity.badRequest().build();
        }

        assertEquals(ResponseEntity.badRequest().build(), response);
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
        verify(cartService).addProductCart(any(CartDTO.class));
    }

    @Test
    void testAddItemWithQuantityEqualToStock() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);
        cartDTO.setProductItemId(2);
        cartDTO.setQuantity(100);

        shoppingCartItem newItem = new shoppingCartItem();
        newItem.setCartId(mockShoppingCart);
        newItem.setProductItemId(mockProductItem);
        newItem.setQty((int) mockProductItem.getQtyInStock());

        when(cartService.findCartItem(eq(1), eq(2))).thenReturn(null);
        when(cartService.addProductCart(any(CartDTO.class))).thenReturn(newItem);

        ResponseEntity<shoppingCartItem> response = cartController.addToCart(cartDTO);

        assertEquals(ResponseEntity.ok(newItem), response);
        assertEquals(mockProductItem.getQtyInStock(), response.getBody().getQty());
        verify(cartService).addProductCart(any(CartDTO.class));
        verify(cartService, never()).updateCartItem(any(shoppingCartItem.class));
    }
}
