package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddProductSizeDTO {
    private int productItemId;
    private int productId;
    private String size;
    private long qty;
    private long price;
    private int isDeleted;
}
