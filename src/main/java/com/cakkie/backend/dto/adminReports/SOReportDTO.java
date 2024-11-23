package com.cakkie.backend.dto.adminReports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SOReportDTO {
//    Shipping method reports
//    Coupons reports

    private String shippingMethod;
    private int orders;
    private String total;
}
