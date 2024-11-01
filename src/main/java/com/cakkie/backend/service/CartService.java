package com.cakkie.backend.service;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.model.shoppingCartItem;

public interface CartService {
    shoppingCartItem updateCartItem(shoppingCartItem cart);
    shoppingCartItem findCartItem(int cartId, int productId);
    shoppingCartItem addProductCart(CartDTO productCart);
    void deleteProductCart(int id);
}
