package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserReviewDTO {
    private int rating;
    private String feedback;
    private Date commentDate;
    private String size;
    private String username;
    private String profilePicture;
    private String reviewImage;
    private int isHide;
}
