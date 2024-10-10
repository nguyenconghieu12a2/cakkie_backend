package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private category categoryID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;
    @Column(name = "product_image", nullable = false, columnDefinition = "TEXT")
    private String productImage;
    @Column(name = "product_rating", nullable = true)
    private int productRating;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "proID")
    private List<productDesInfo> productDesInfoList;

    @OneToMany(mappedBy = "proId")
    private List<productItem> productItemList;

    @OneToMany(mappedBy = "productId")
    private List<productCart> productCartList;
}
