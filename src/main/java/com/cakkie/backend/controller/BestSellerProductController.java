package com.cakkie.backend.controller;

import com.cakkie.backend.dto.BestSellerProductDTO;
import com.cakkie.backend.service.BestSellerProductService;
import com.cakkie.backend.service.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BestSellerProductController {
    private final BestSellerProductService bestSellerProductService;

    @Autowired
    private UserReviewService userReviewService;
    @GetMapping("/best-seller")
    public ResponseEntity<List<BestSellerProductDTO>> getBestSellerProducts() {
        List<BestSellerProductDTO> bestSellers = bestSellerProductService.getBestSellerProducts();

        // Add average rating for each best-seller product
        for (BestSellerProductDTO product : bestSellers) {
            double averageRating = userReviewService.getAverageRatingForProduct(product.getProductId());
            product.setProductRating(averageRating);
        }

        return ResponseEntity.ok(bestSellers);
    }
}
