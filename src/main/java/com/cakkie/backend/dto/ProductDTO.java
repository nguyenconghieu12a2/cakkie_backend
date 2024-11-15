package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private String description;
    private String productImage;
    private int productRating;
    private List<ProductItemDTO> productItem;
    private List<ProductTitleDTO> productTitle;
    private List<ProductInfoDTO> productInfo;
}