package com.cakkie.backend.controller.adminReview;

import com.cakkie.backend.dto.adminReview.ReviewDTO;
import com.cakkie.backend.service.adminReview.AdminReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AdminReviewController {
    private AdminReviewService adminReviewService;

    public AdminReviewController(AdminReviewService adminReviewService) {
        this.adminReviewService = adminReviewService;
    }

    @GetMapping("/reviews/pending")
    public List<ReviewDTO> getPendingReviews() {
        return adminReviewService.getPendingReviews();
    }

    @GetMapping("/reviews/approved")
    public List<ReviewDTO> getApprovedReviews() {
        return adminReviewService.getApprovedReviews();
    }

    @GetMapping("/reviews/rejected")
    public List<ReviewDTO> getRejectedReviews() {
        return adminReviewService.getRejectedReviews();
    }

    // Update review status
    @PostMapping("/reviews/update-status")
    public String updateReviewStatus(@RequestParam int reviewId, @RequestParam int status) {
        return adminReviewService.updateReviewStatus(reviewId, status);
    }

    // Ban a user
    @PostMapping("/users/ban")
    public String banUser(@RequestParam int userId, @RequestParam String bannedReason) {
        return adminReviewService.banUser(userId, bannedReason);
    }
}