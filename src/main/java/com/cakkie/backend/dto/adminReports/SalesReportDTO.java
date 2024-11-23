package com.cakkie.backend.dto.adminReports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDTO {
    private String startDate;
    private String endDate;
    private String revenue;
}
