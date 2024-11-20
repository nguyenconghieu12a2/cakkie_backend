package com.cakkie.backend.controller;

import com.cakkie.backend.dto.ReviewRequest;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.service.UserReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserReviewController {
    private UserReviewService userReviewService;

    public UserReviewController(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }

    @PostMapping(value = "/review/add", consumes = {"multipart/form-data"})
    public ResponseEntity<userReview> addReview(@ModelAttribute ReviewRequest reviewRequest) throws IOException {
        userReview review = userReviewService.addReview(
                reviewRequest.getUserId(),
                reviewRequest.getOrderProductId(),
                reviewRequest.getRating(),
                reviewRequest.getFeedback(),
                reviewRequest.getImageFile(),
                reviewRequest.getIsHide()
        );
        return ResponseEntity.ok(review);
    }
}
