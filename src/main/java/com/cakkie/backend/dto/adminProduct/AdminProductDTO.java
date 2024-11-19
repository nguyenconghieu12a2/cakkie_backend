package com.cakkie.backend.dto.adminProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDTO {
    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private String description;
    private String productImage;
    private int productRating;
    private List<AdminProductItemDTO> productItem;
    private List<AdminProductTitleDTO> productTitle;
    private List<AdminProductInfoDTO> productInfo;
}