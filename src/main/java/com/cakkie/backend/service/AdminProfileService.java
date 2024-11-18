package com.cakkie.backend.service;

import com.cakkie.backend.model.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.AdminProfileRepo;
import org.springframework.web.multipart.MultipartFile;
import com.cakkie.backend.dto.AdminProfileDTO;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProfileService {

    private final AdminProfileRepo adminProfileRepo;
    private static final String IMG_URLL = "D:/Cakkie-Project/Project_Frontend/cakkie_frontend/public/images/admin-avt/";

    public Optional<admin> findById(int id) {
        return adminProfileRepo.findById(id);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
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

    public AdminProfileDTO updateAdminProfile(int id, String firstName, String lastName, String username, String email, MultipartFile imageFile) throws IOException {
        Optional<admin> optionalAdmin = adminProfileRepo.findById(id);

        if (optionalAdmin.isPresent()) {
            admin existingAdmin = optionalAdmin.get();

            // Update profile fields
            existingAdmin.setFirstname(firstName);
            existingAdmin.setLastname(lastName);
            existingAdmin.setUsername(username);
            existingAdmin.setEmail(email);


            // Handle image update, if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageName = saveImage(imageFile);
                existingAdmin.setAvatarImg(imageName);
            }

            // Save the updated admin profile
            admin updatedAdmin = adminProfileRepo.save(existingAdmin);

            // Convert updated admin to AdminProfileDTO
            return new AdminProfileDTO(
                    updatedAdmin.getId(),
                    updatedAdmin.getFirstname(),
                    updatedAdmin.getLastname(),
                    updatedAdmin.getUsername(),
                    updatedAdmin.getEmail(),
                    updatedAdmin.getAvatarImg()
            );
        } else {
            throw new RuntimeException("Admin with username " + username + " not found");
        }
    }

    public String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public AdminProfileDTO changePassword(int id, String oldPassword, String newPassword){
        Optional<admin> opAdmin = adminProfileRepo.findById(id);

        if(opAdmin.isPresent()) {
            admin exsitingAdmin = opAdmin.get();

            String hashOldPassword = getMd5(oldPassword);

            if(!exsitingAdmin.getPassword().equals(hashOldPassword)) {
                throw new RuntimeException("Incorrect old password");
            }

            String hashNewPassword = getMd5(newPassword);
            exsitingAdmin.setPassword(hashNewPassword);

            admin updateAdmin = adminProfileRepo.save(exsitingAdmin);

            return new AdminProfileDTO(
                    updateAdmin.getId(),
                    updateAdmin.getFirstname(),
                    updateAdmin.getLastname(),
                    updateAdmin.getUsername(),
                    updateAdmin.getEmail(),
                    updateAdmin.getAvatarImg()
            );
        }else{
            throw new RuntimeException("Admin with id " + id + " not found");
        }
    }
}
