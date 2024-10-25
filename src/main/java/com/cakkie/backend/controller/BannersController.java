package com.cakkie.backend.controller;

import com.cakkie.backend.model.banners;
import com.cakkie.backend.service.BannersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BannersController {
    private final BannersService bannersService;

    @GetMapping("/banners")
    public ResponseEntity<List<banners>> getAllBanners() {
        return new  ResponseEntity<>(bannersService.getAllBanners(), HttpStatus.OK);
    }

    @PostMapping("/add-banners")
    public banners addBanners(@RequestBody banners banners) {
        return bannersService.addBanners(banners);
    }
    @PutMapping("/update-banners/{id}")
    public banners updateBanners(@RequestBody banners banners,@PathVariable int id) {
        return bannersService.updateBanners(banners, id);
    }

    @DeleteMapping("/delete-banners/{id}")
    public void deleteBanners(@PathVariable int id) {
        bannersService.deleteBanners(id);
    }
}
