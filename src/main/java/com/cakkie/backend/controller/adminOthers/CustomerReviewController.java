package com.cakkie.backend.controller.adminOthers;

import com.cakkie.backend.dto.adminOthers.CustomerReviewDTO;
import com.cakkie.backend.model.userReview;
import com.cakkie.backend.service.adminOthers.CustomerReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerReviewController {
    private final CustomerReviewService customerReviewService;

    @GetMapping("/product-reviews/{id}")
    public ResponseEntity<List<CustomerReviewDTO>> getProductReviews(@PathVariable int id) {
        List<CustomerReviewDTO> customerReview = customerReviewService.getCustomerReviewByProduct(id);
        return ResponseEntity.ok(customerReview);
    }

    @PostMapping("/add-customer-review")
    public ResponseEntity<userReview> addCustomerReview(
            @RequestParam("orderProductId") int orderLineId,
            @RequestParam("customerId") int customerId,
            @RequestParam("rating") int rating,
            @RequestParam("feedback") String feedback,
            @RequestParam("imageFile") MultipartFile image,
            @RequestParam("isHide") int isHide
    ) {
        try {
            userReview addedReview = customerReviewService.addCustomerReview(orderLineId, customerId, rating, feedback, image, isHide);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
