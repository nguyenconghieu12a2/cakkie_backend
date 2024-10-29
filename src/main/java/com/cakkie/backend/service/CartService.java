package com.cakkie.backend.service;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.model.shoppingCartItem;

public interface CartService {
    shoppingCartItem addProductCart(CartDTO productCart);
    void deleteProductCart(int id);
}
