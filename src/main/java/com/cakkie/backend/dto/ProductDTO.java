package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private int id;
    private String cateName;
    private String name;
    private String productImage;
    private long qtyInStock;
    private long price;
    private String size;
    private String productItemImage;
    private int isDeleted;

    public ProductDTO() {}

    public ProductDTO(int id, String cateName, String name, String productImage, long qtyInStock, long price, String size, String productItemImage ,int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.name = name;
        this.productImage = productImage;
        this.qtyInStock = qtyInStock;
        this.price = price;
        this.size = size;
        this.productItemImage = productItemImage;
        this.isDeleted = isDeleted;
    }
}
