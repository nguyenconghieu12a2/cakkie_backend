package com.cakkie.backend.dto.adminDiscount;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDiscreteDiscountDTO {
    private int id;
    private String name;
    private String description;
    private String discountRate;
    private String startDate;
    private String endDate;
}
