package com.cakkie.backend.dto.adminReports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilter {
    private String startDate;
    private String endDate;

}
