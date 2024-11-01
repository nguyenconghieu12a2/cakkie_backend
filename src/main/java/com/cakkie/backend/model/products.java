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

public class products {
    private int id;
    private int productID;
    private int categoryID;
    private int productItemId;
    private String name;
    private String description;
    private String categoryName;
    private long price;
    private String productImage;
    private int productRating;
    private String size;
    private long quantityStock;
    private List<productsDescription> Descriptions;
    private double discount;
    private String desInfo;
    private String desTitleName;
    private int isDeleted;
}
