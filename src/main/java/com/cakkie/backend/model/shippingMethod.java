package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipping_method")
public class shippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private long price;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "shippingMethodId")
    private List<shopOrder> shopOrdersList;
}
