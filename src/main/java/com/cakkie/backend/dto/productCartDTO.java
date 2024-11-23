package com.cakkie.backend.dto;

import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.userSite;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class productCartDTO {
    private int id;
    private int userId;
    private int cartId;
    private int productItemId;
    private int qty;
    private String note;
    private int isDeleted;

    public productCartDTO() {
    }

    public productCartDTO(int id, int userId, int cartId, int productItemId, int qty, String note) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.productItemId = productItemId;
        this.qty = qty;
        this.note = note;
    }

    public productCartDTO(int id, int userId, int cartId, int productItemId, int qty, String note, int isDeleted) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.productItemId = productItemId;
        this.qty = qty;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(int productItemId) {
        this.productItemId = productItemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
