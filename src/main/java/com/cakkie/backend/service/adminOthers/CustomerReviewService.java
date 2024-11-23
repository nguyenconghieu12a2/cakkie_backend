package com.cakkie.backend.service.adminOthers;

import com.cakkie.backend.dto.adminOthers.CustomerReviewDTO;
import com.cakkie.backend.exception.adminOthers.ProductReviewNotFoundException;
import com.cakkie.backend.model.orderLine;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.model.userReviewStatus;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.repository.adminOthers.customerRV.CustomerReviewRepo;
import com.cakkie.backend.repository.adminOthers.customerRV.OrderLineReviewRepo;
import com.cakkie.backend.repository.adminOthers.customerRV.ReviewStatusRepo;
import com.cakkie.backend.repository.adminOthers.customerRV.UserSiteReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerReviewService {
    private static final String IMG_URLL = "D:/Cakkie-Project/Project_Frontend/cakkie_frontend/public/images/";
    private final CustomerReviewRepo customerReviewRepo;
    private final OrderLineReviewRepo orderLineReviewRepo;
    private final ReviewStatusRepo reviewStatusRepo;
    private final UserSiteReviewRepo userSiteReviewRepo;

    public List<CustomerReviewDTO> getCustomerReviewByProduct(int id) {
        List<Object[]> reviewData = customerReviewRepo.findByCustomerId(id);
        Map<Integer, CustomerReviewDTO> reviewsMap = new HashMap<>();

        if(reviewData.isEmpty()){
            throw new ProductReviewNotFoundException("Product review not found");
        }

        for (Object[] row : reviewData) {
            int reviewId = (Integer) row[0];
            int customerId = ((Number) row[1]).intValue();
            String customerName = (String) row[2];
            int productId = ((Number) row[3]).intValue();
            int productRating = ((Number) row[4]).intValue();
            String feedback = (String) row[5];
            String reviewImage = (String) row[6];
            String approvedDate = (String) row[7];
            String validDate = (String) row[8];
            int isHide = ((Number) row[9]).intValue();

            CustomerReviewDTO customerReviewDTO = reviewsMap.getOrDefault(reviewId, new CustomerReviewDTO(
                    reviewId, customerId, customerName,
                    productId, productRating, feedback, reviewImage,
                    approvedDate, validDate,
                    isHide
            ));
            reviewsMap.put(customerId, customerReviewDTO);
        }
        return new ArrayList<>(reviewsMap.values());
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

    public userReview addCustomerReview(int orderLineId, int customerId, int rating, String feedback, MultipartFile image, Integer isHide) throws IOException {
        String imagePath = (image != null) ? saveImage(image) : "";

        userReview userReview = new userReview();

        orderLine orderProduct = orderLineReviewRepo.findById(orderLineId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid orderProductId"));;
        userSite customer = userSiteReviewRepo.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customerId"));
        userReviewStatus status = reviewStatusRepo.findById(2)
                .orElseThrow(() -> new IllegalArgumentException("Invalid statusId"));

        userReview.setOrderProductId(orderProduct);
        userReview.setUserId(customer);
        userReview.setRating(rating);

        userReview.setFeedback(
                (feedback != null && !feedback.isEmpty())
                        ? feedback
                        : ""
        );

        userReview.setReviewImage(imagePath);
        userReview.setStatusId(status);
        userReview.setCommentDate(new Date()); // Set the current date

        userReview.setIsHide(isHide != null ? isHide : -1);

        userReview.setIsDeleted(1);
        return customerReviewRepo.save(userReview);
    }
}
