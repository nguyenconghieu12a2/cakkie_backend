package com.cakkie.backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewRequest {
    private int userId;
    private int orderProductId;
    private int rating;
    private String feedback;
    private MultipartFile imageFile;
    private int isHide;
}
