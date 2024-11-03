package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewDTO {
    private int reviewId;
    private int customerId;
    private String customerName;
    private int productId;
    private int productRating;
    private String feedback;
    private String reviewImage;
    private String approvedDate;
    private String validDate;
    private int isHide;
}
