package com.cakkie.backend.dto.adminProduct;

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
    private long qtyInStock;
    private long price;
    private int isDeleted;
}
