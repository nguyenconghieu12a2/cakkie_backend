package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewReportDTO {
    private String startDate;
    private String endDate;
    private int penddingReviews;
    private int approvedReviews;
    private int rejectedReviews;
    private int total;
}
