package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.cakkie.backend.model.productsDescription;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productID;
    private int categoryID;
    private String name;
    private String description;
    private String categoryName;
    private long price;
    private String productImage;
    private int productRating;
    private String size;
    private long quantityStock;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "desId")
    private List<productsDescription> Descriptions;
    private String desInfo;
    private String desTitleName;
    private int isDeleted;
}
