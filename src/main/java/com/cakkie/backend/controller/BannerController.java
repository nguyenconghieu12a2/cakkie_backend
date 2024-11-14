package com.cakkie.backend.controller;

import com.cakkie.backend.model.banners;
import com.cakkie.backend.service.BannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class BannerController {
    private BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/banners")
    public ResponseEntity<List<banners>> getActiveBanners() {
        List<banners> banners = bannerService.getActiveBanners();
        return ResponseEntity.ok(banners);
    }
}
