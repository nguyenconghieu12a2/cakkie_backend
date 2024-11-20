package com.cakkie.backend.service;

import com.cakkie.backend.dto.UserReviewDTO;
import com.cakkie.backend.model.orderLine;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.model.userReviewStatus;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.repository.UserReviewRepository;
import com.cakkie.backend.repository.UserReviewStatusRepository;
import com.cakkie.backend.repository.UserSiteRepository;
import com.cakkie.backend.repository.orderLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserReviewService {
    private static final String IMG_URLL = "D:/Study/GitProject/FE_SWP/cakkie_frontend/public/images/";
    private UserReviewRepository userReviewRepository;
    private orderLineRepository OrderLineRepository;
    private UserSiteRepository userSiteRepository;
    private UserReviewStatusRepository userReviewStatusRepository;

    public UserReviewService(UserReviewRepository userReviewRepository, orderLineRepository orderLineRepository, UserSiteRepository userSiteRepository, UserReviewStatusRepository userReviewStatusRepository) {
        this.userReviewRepository = userReviewRepository;
        OrderLineRepository = orderLineRepository;
        this.userSiteRepository = userSiteRepository;
        this.userReviewStatusRepository = userReviewStatusRepository;
    }

    public List<UserReviewDTO> getReviewsForProduct(int productId) {
        List<userReview> reviews = userReviewRepository.findReviewsByProductId(productId);
        return reviews.stream().map(review -> {
            String reviewImagePath = null;

            // Include the review image only if it's not null or empty
            if (review.getReviewImage() != null && !review.getReviewImage().isEmpty()) {
                reviewImagePath = review.getReviewImage(); // Adjusted relative path for frontend access
            }

            return new UserReviewDTO(
                    review.getRating(),
                    review.getFeedback(),
                    review.getCommentDate(),
                    review.getOrderProductId().getProductItemId().getSize(),
                    review.getUserId().getUsername(),
                    review.getUserId().getImage(),
                    reviewImagePath,
                    review.getIsHide()
            );
        }).collect(Collectors.toList());
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

    private String saveImage(MultipartFile imageFile) throws IOException {
        // Check if the image file is empty or null
        if (imageFile == null || imageFile.isEmpty()) {
            return ""; // Return an empty string if no image is provided
        }

        // Ensure the directory exists
        File directory = new File(IMG_URLL);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Save the file to the directory
        String originalFilename = imageFile.getOriginalFilename();
        String baseName = originalFilename != null ? originalFilename.replaceAll("\\.[^.]+$", "") : "image";
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        // Initialize the file path with the original name
        Path path = Paths.get(IMG_URLL + originalFilename);
        int counter = 1;

        // Check if the file with this name already exists, and modify the name if necessary
        while (Files.exists(path)) {
            String newFilename = baseName + "(" + counter + ")" + extension;
            path = Paths.get(IMG_URLL + newFilename);
            counter++;
        }

        // Save the file with the final available name
        Files.write(path, imageFile.getBytes());
        return path.getFileName().toString();
    }

    public userReview addReview(int userId, int orderProductId, int rating, String feedback, MultipartFile imageFile, int isHide) throws IOException {
        Optional<orderLine> orderLineOptional = OrderLineRepository.findById(orderProductId);
        Optional<userSite> userSiteOptional = userSiteRepository.findById(userId);
        Optional<userReviewStatus> defaultStatusOptional = userReviewStatusRepository.findById(2);

        if (orderLineOptional.isEmpty() || userSiteOptional.isEmpty() || defaultStatusOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid user, order product ID, or review status");
        }

        userReview review = new userReview();
        review.setOrderProductId(orderLineOptional.get());
        review.setUserId(userSiteOptional.get());
        review.setRating(rating);
        review.setFeedback(feedback);
        review.setStatusId(defaultStatusOptional.get()); // Set default status
        review.setCommentDate(new Date());
        review.setIsHide(isHide); // Accept dynamic value for isHide
        review.setIsDeleted(1);

        // Handle the image file
        String imageFilename = saveImage(imageFile);
        review.setReviewImage(imageFilename);

        return userReviewRepository.save(review);
    }
}
