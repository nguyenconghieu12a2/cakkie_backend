package com.cakkie.backend.controller;

import com.cakkie.backend.model.banners;
import com.cakkie.backend.service.BannersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BannersController {
    private final BannersService bannersService;
//    private static final String UPLOAD_DIR = "D:\\Cakkie-Project\\Project_Frontend\\cakkie_frontend\\public";

    @GetMapping("/banners")
    public ResponseEntity<List<banners>> getAllBanners() {
        return new  ResponseEntity<>(bannersService.getAllBanners(), HttpStatus.OK);
    }


    @PostMapping("/add-banners")
    public ResponseEntity<banners> addBanner(
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image,
            @RequestParam("link") String link
    ) {
        try {
            banners newBanner = bannersService.addBanners(title, image, link);
            return ResponseEntity.ok(newBanner);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update-banners/{id}")
    public ResponseEntity<banners> updateBanners(@RequestParam("title") String title,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam("link") String link,
                                                 @PathVariable int id) {
        try {
            banners updatedBanner = bannersService.updateBanners( title, image, link, id);
            return ResponseEntity.ok(updatedBanner);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/delete-banners/{id}")
    public void deleteBanners(@PathVariable int id) {
        bannersService.deleteBanners(id);
    }
}
