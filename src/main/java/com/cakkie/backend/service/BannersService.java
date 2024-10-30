package com.cakkie.backend.service;

import com.cakkie.backend.exception.BannerNotFoundException;
import com.cakkie.backend.model.banners;
import com.cakkie.backend.repository.BannerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannersService {
    private static final String IMG_URL = "D:/Cakkie-Project/Project_Frontend/cakkie_frontend/public/images/";

    private final BannerRepo bannerRepo;

    public List<banners> getAllBanners() {
        return bannerRepo.findAll();
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        // Ensure the directory exists
        File directory = new File(IMG_URL);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Save the file to the directory
        String originalFilename = imageFile.getOriginalFilename();
        Path path = Paths.get(IMG_URL + originalFilename);
        Files.write(path, imageFile.getBytes());

        return originalFilename;
    }

    public banners updateBanners(String title, MultipartFile imageFile, String link, int id) throws IOException{
        Optional<banners> existsBannerOpt = bannerRepo.findById(id);
        if (!existsBannerOpt.isPresent()) {
            throw new IllegalArgumentException("Banner not found with id: " + id);
        }
        banners existingBanner = existsBannerOpt.get();
        existingBanner.setTitle(title);
        existingBanner.setLink(link);

        if (imageFile != null && !imageFile.isEmpty()) {
            String newImagePath = saveImage(imageFile);
            existingBanner.setImage(newImagePath); // Update with the new image
        }

        return bannerRepo.save(existingBanner);

    }

    public banners addBanners(String title, MultipartFile imageFile, String link) throws IOException {
        String imagePath = saveImage(imageFile);

        banners banner = new banners();
        banner.setTitle(title);
        banner.setLink(link);
        banner.setImage(imagePath);
        banner.setIsDeleted(1);

        return bannerRepo.save(banner);
    }


    @Transactional
    public void deleteBanners(int id) {
        if(!bannerRepo.existsById(id)) {
            throw new BannerNotFoundException("Sorry, Banner not found!");
        }
        bannerRepo.deleteById(id);
    }
}
