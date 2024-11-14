package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailCommonDiscountDTO {
    private int discountId;
    private String discountName;
    private String description;
    private String discountRate;
    private String startDate;
    private String endDate;
    private List<CommonCateAppliedDTO> commonCateApplieds;
}
