package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_cart")
public class productCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userSite userId;
    @Column(name = "product_item_id", nullable = false)
    private int productItemId;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private product productId;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
