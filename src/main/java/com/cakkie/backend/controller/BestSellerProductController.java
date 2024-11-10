package com.cakkie.backend.controller;

import com.cakkie.backend.dto.BestSellerProductDTO;
import com.cakkie.backend.service.BestSellerProductService;
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

    @GetMapping("/best-seller")
    public ResponseEntity<List<BestSellerProductDTO>> getBestSellerProducts() {
        return ResponseEntity.ok(bestSellerProductService.getBestSellerProducts());
    }
}
