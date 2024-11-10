package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDTO {
    private int desTitleID;
    private String desInfo;
    private int isDelete;
}