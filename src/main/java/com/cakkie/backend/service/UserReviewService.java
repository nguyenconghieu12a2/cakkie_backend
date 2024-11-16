package com.cakkie.backend.service;

import com.cakkie.backend.dto.UserReviewDTO;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.repository.UserReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserReviewService {
    private UserReviewRepository userReviewRepository;

    public UserReviewService(UserReviewRepository userReviewRepository) {
        this.userReviewRepository = userReviewRepository;
    }

    public List<UserReviewDTO> getReviewsForProduct(int productId) {
        List<userReview> reviews = userReviewRepository.findReviewsByProductId(productId);
        return reviews.stream().map(review -> new UserReviewDTO(
                review.getRating(),
                review.getFeedback(),
                review.getCommentDate(),
                review.getOrderProductId().getProductItemId().getSize()
        )).collect(Collectors.toList());
    }

    public double getAverageRatingForProduct(int productId) {
        // Fetch all reviews for the product
        List<userReview> reviews = userReviewRepository.findReviewsByProductId(productId);

        // Calculate average rating
        return reviews.stream()
                .filter(review -> review.getRating() > 0) // Exclude reviews with 0 or no rating
                .mapToInt(userReview::getRating) // Extract ratings
                .average() // Compute average
                .orElse(0.0); // Default to 0.0 if no valid ratings
    }
}
