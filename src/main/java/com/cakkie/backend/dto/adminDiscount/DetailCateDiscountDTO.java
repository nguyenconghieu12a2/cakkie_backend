package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailCateDiscountDTO {
    private int discountCateId;
    private int discountId;
    private int categoryId;
    private String discountName;
    private String discountRate;
    private String startDate;
    private String endDate;
}
