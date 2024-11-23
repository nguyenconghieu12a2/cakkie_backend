package com.cakkie.backend.service.adminReview;

import com.cakkie.backend.dto.adminReview.ReviewDTO;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.model.userReviewStatus;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.model.userStatus;
import com.cakkie.backend.repository.adminReview.AdminReviewRepository;
import com.cakkie.backend.repository.adminReview.AdminReviewStatusRepository;
import com.cakkie.backend.repository.adminReview.ReviewUserRepository;
import com.cakkie.backend.repository.adminReview.ReviewUserStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminReviewService {
    private AdminReviewRepository adminReviewRepository;
    private ReviewUserRepository reviewUserRepository;
    private ReviewUserStatusRepository reviewUserStatusRepository;
    private AdminReviewStatusRepository adminReviewStatusRepository;

    public AdminReviewService(AdminReviewRepository adminReviewRepository, ReviewUserRepository reviewUserRepository, ReviewUserStatusRepository reviewUserStatusRepository, AdminReviewStatusRepository adminReviewStatusRepository) {
        this.adminReviewRepository = adminReviewRepository;
        this.reviewUserRepository = reviewUserRepository;
        this.reviewUserStatusRepository = reviewUserStatusRepository;
        this.adminReviewStatusRepository = adminReviewStatusRepository;
    }

    public List<ReviewDTO> getApprovedReviews() {
        List<userReview> approvedReviews = adminReviewRepository.findApprovedReviews();
        return approvedReviews.stream().map(review -> new ReviewDTO(
                review.getId(),
                review.getUserId().getUsername(),
                review.getUserId().getId(),
                review.getRating(),
                review.getFeedback(),
                review.getReviewImage(),
                review.getOrderProductId().getProductItemId().getProId().getName(),
                review.getOrderProductId().getProductItemId().getSize(),
                review.getApprovedDate(), // Include approved date
                null // Reject date is null for approved reviews
        )).collect(Collectors.toList());
    }

    public List<ReviewDTO> getRejectedReviews() {
        List<userReview> rejectedReviews = adminReviewRepository.findRejectedReviews();
        return rejectedReviews.stream().map(review -> new ReviewDTO(
                review.getId(),
                review.getUserId().getUsername(),
                review.getUserId().getId(),
                review.getRating(),
                review.getFeedback(),
                review.getReviewImage(),
                review.getOrderProductId().getProductItemId().getProId().getName(),
                review.getOrderProductId().getProductItemId().getSize(),
                null, // Approved date is null for rejected reviews
                review.getRejectedDate() // Include reject date
        )).collect(Collectors.toList());
    }

    public List<ReviewDTO> getPendingReviews() {
        List<userReview> pendingReviews = adminReviewRepository.findPendingReviews();
        return pendingReviews.stream().map(review -> new ReviewDTO(
                review.getId(),
                review.getUserId().getUsername(),
                review.getUserId().getId(), // Include userId
                review.getRating(),
                review.getFeedback(),
                review.getReviewImage(),
                review.getOrderProductId().getProductItemId().getProId().getName(),
                review.getOrderProductId().getProductItemId().getSize(),
                null,
                null
        )).collect(Collectors.toList());
    }

    public String banUser(int userId, String bannedReason) {
        // Fetch user by ID
        Optional<userSite> optionalUser = reviewUserRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return "User not found.";
        }

        userSite user = optionalUser.get();

        // Fetch the 'Banned' status (id = 2)
        Optional<userStatus> optionalStatus = reviewUserStatusRepository.findById(2);

        if (optionalStatus.isEmpty()) {
            return "User status for 'banned' not found.";
        }

        // Update user status and set banned reason
        user.setStatusId(optionalStatus.get());
        user.setBannedReason(bannedReason);
        reviewUserRepository.save(user);

        return "User banned successfully.";
    }

    public String updateReviewStatus(int reviewId, int status) {
        // Validate status (only 1 or 3 are allowed)
        if (status != 1 && status != 3) {
            return "Invalid status. Status must be 1 (approved) or 3 (rejected).";
        }

        // Fetch the review by ID
        Optional<userReview> optionalReview = adminReviewRepository.findById(reviewId);

        if (optionalReview.isEmpty()) {
            return "Review not found.";
        }

        userReview review = optionalReview.get();

        // Fetch the review status from the database
        Optional<userReviewStatus> optionalStatus = adminReviewStatusRepository.findById(status);

        if (optionalStatus.isEmpty()) {
            return "Review status not found.";
        }

        // Update the review's status
        review.setStatusId(optionalStatus.get());

        LocalDateTime currentDateTime = LocalDateTime.now();
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        if (status == 1) {
            review.setApprovedDate(currentDate); // Set the approved date
        } else if (status == 3) {
            review.setRejectedDate(currentDate); // Set the rejected date
        }

        adminReviewRepository.save(review);

        return status == 1 ? "Review approved successfully." : "Review rejected successfully.";
    }
}