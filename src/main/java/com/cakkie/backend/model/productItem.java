package com.cakkie.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_item")
public class productItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "pro_id", nullable = true)
    @JsonBackReference
    private product proId;
    @Column(name = "size", nullable = true, length = 10)
    private String size;
    @Column(name = "qty_in_stock", nullable = false)
    private long qtyInStock;
    @Column(name = "product_image", nullable = false, columnDefinition = "TEXT")
    private String productImage;
    @Column(name = "price", nullable = false)
    private long price;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "productItemId")
    @JsonIgnore
    private List<shoppingCartItem> shoppingCartItemsList;

    @OneToMany(mappedBy = "productItemId")
    @JsonIgnore
    private List<orderLine> orderLineList;
}
