package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
     private int userId;
     private int cartId;
     private int productItemId;
     private int quantity;
     private String note;

     public CartDTO() {

     }

    public CartDTO(int userId, int cartId, int productItemId, int quantity, String note) {
        this.userId = userId;
        this.cartId = cartId;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.note = note;
    }

    public CartDTO(int userId, int productItemId, int quantity, String note) {
        this.userId = userId;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.note = note;
    }
}
