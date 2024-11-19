package com.cakkie.backend.dto.adminProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductTitleDTO {
    private int desTitleID;
    private String desTitleName;
    private int isDelete;
}
