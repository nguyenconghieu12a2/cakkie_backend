package com.cakkie.backend.implement;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.shoppingCartItem;
import com.cakkie.backend.repository.CartRepository;
import com.cakkie.backend.repository.ProductItemRepository;
import com.cakkie.backend.repository.ProductRepository;
import com.cakkie.backend.repository.ShoppingCartRepository;
import com.cakkie.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartImplement implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public shoppingCartItem updateCartItem(shoppingCartItem cartItem) {
        shoppingCartItem existingCartItem = cartRepository.findById(cartItem.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + cartItem.getId()));

        existingCartItem.setQty(cartItem.getQty());

        return cartRepository.save(existingCartItem);
    }


    @Override
    public shoppingCartItem findCartItem(int cartId, int productId) {
        return cartRepository.findCartItem(cartId, productId);
    }

    @Override
    public shoppingCartItem addProductCart(CartDTO productCart) {
        // Fetch the ProductItem entity using the productItemId
        productItem productItem = productItemRepository.findById(productCart.getProductItemId())
                .orElseThrow(() -> new RuntimeException("ProductItem not found"));

        shoppingCart cart = shoppingCartRepository.findById(productCart.getCartId())
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));
        // Convert DTO to Entity
        shoppingCartItem cartItem = new shoppingCartItem();
        cartItem.setCartId(cart);
        cartItem.setProductItemId(productItem);
        cartItem.setQty(productCart.getQuantity());
        cartItem.setNote(productCart.getNote());

        // Save to the repository
        return cartRepository.save(cartItem);
    }

    @Override
    public void deleteProductCart(int id) {
        shoppingCartItem cart = cartRepository.getProductCartById(id);
        cartRepository.delete(cart);
    }
}
