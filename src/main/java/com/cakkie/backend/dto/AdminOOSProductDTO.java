package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOOSProductDTO {
    private int productItemId;
    private String productName;
    private String size;
    private String productImage;
    private int quantity;
    private String price;
}
