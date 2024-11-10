package com.cakkie.backend.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SaveIMGBanner {
    private static final String IMG_URL = "D:/Cakkie-Project/Project_Frontend/cakkie_frontend/public/images/";

    public String saveImage(MultipartFile imageFile) throws IOException {
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
}
