package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CCOPReportDTO {
//    Order cancel reports
//    Customer Order reports
//    Orders reports
//    Products sales reports

    private String startDate;
    private String endDate;
    private int products;
    private int quantity;
    private String total;
}
