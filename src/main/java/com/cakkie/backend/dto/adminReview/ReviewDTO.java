package com.cakkie.backend.dto.adminReview;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private int id;
    private final String username;
    private int userId;
    private final int rating;
    private final String feedback;
    private final String reviewImage;
    private final String productName;
    private final String productSize;
    private Date approvedDate;
    private Date rejectDate;
}
