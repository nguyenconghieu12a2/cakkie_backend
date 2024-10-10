package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_cart_item")
public class shoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private shoppingCart cartId;
    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private productItem productItemId;
    @Column(name = "qty", nullable = false)
    private int qty;
    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    private String note;
}
