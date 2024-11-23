package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDiscountListDTO {
    private int discountId;
    private String discountName;
    private String discountRate;
    private String startDate;
    private String endDate;
}
