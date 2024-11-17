package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemSizeDTO {
    private int proId;
    private int proItemId;
    private String size;
    private long price;
    private long qtyInStock;
}
