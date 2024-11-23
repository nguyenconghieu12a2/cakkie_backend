package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount_category")
public class discountCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private category categoryId;
    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private discount discountId;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
