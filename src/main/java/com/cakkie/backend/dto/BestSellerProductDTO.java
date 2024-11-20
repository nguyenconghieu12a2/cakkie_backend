package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerProductDTO {
    private int productId;
    private String productName;
    private String productImage;
    private double productRating;
    private String productPrice;
}
