package com.cakkie.backend.service;

import com.cakkie.backend.exception.BannerNotFoundException;
import com.cakkie.backend.model.banners;
import com.cakkie.backend.repository.AdminBannerRepo;
import com.cakkie.backend.utils.SaveIMGBanner;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBannersService {
//    private static final String IMG_URL = "D:/Cakkie-Project/Project_Frontend/cakkie_frontend/public/images/";

    private final AdminBannerRepo adminBannerRepo;
    private final SaveIMGBanner saveIMGBanner;

    public List<banners> getAllBanners() {
        return adminBannerRepo.findAll();
    }

//    private String saveImage(MultipartFile imageFile) throws IOException {
//        // Ensure the directory exists
//        File directory = new File(IMG_URL);
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//        // Save the file to the directory
//        String originalFilename = imageFile.getOriginalFilename();
//        Path path = Paths.get(IMG_URL + originalFilename);
//        Files.write(path, imageFile.getBytes());
//
//        return originalFilename;
//    }

    public banners updateBanners(String title, MultipartFile imageFile, String link, int id) throws IOException{
        Optional<banners> existsBannerOpt = adminBannerRepo.findById(id);
        if (!existsBannerOpt.isPresent()) {
            throw new IllegalArgumentException("Banner not found with id: " + id);
        }
        banners existingBanner = existsBannerOpt.get();
        existingBanner.setTitle(title);
        existingBanner.setLink(link);

        if (imageFile != null && !imageFile.isEmpty()) {
            String newImagePath = saveIMGBanner.saveImage(imageFile);
            existingBanner.setImage(newImagePath); // Update with the new image
        }

        return adminBannerRepo.save(existingBanner);

    }

    public banners addBanners(String title, MultipartFile imageFile, String link) throws IOException {
        String imagePath = saveIMGBanner.saveImage(imageFile);

        banners banner = new banners();
        banner.setTitle(title);
        banner.setLink(link);
        banner.setImage(imagePath);
        banner.setIsDeleted(1);

        return adminBannerRepo.save(banner);
    }


    @Transactional
    public void deleteBanners(int id) {
        if(!adminBannerRepo.existsById(id)) {
            throw new BannerNotFoundException("Sorry, Banner not found!");
        }
        adminBannerRepo.deleteById(id);
    }
}
