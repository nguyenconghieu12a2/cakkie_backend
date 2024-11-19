package com.cakkie.backend.dto.adminProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductInfoDTO {
    private int desTitleID;
    private String desInfo;
    private int isDelete;
}