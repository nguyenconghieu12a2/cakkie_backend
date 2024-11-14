package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonCateAppliedDTO {
    private int cateId;
    private String cateName;
}
