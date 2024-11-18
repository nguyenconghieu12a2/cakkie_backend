package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonCateAppliedDTO {
    private int cateDisId;
    private int cateId;
    private String cateName;
    private int isDeleted;

    public CommonCateAppliedDTO(int cateDisId, int cateId, String cateName, int isDeleted) {
        this.cateDisId = cateDisId;
        this.cateId = cateId;
        this.cateName = cateName;
        this.isDeleted = isDeleted;
    }

    public CommonCateAppliedDTO(int cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }
}
