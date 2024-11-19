package com.cakkie.backend.dto.adminProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductItemSizeDTO {
    private int proId;
    private int proItemId;
    private String size;
    private long price;
    private long qtyInStock;
}
