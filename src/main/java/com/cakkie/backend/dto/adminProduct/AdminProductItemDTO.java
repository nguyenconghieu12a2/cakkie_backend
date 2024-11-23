package com.cakkie.backend.dto.adminProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductItemDTO {
    private int id;
    private String size;
    private int quantity;
    private long price;
    private int isDelete;
}
