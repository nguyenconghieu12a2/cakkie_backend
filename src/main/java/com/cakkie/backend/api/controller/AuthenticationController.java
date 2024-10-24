package com.cakkie.backend.api.controller;

import com.cakkie.backend.api.model.LoginBody;
import com.cakkie.backend.api.model.LoginResponse;
import com.cakkie.backend.api.model.RegistrationBody;
import com.cakkie.backend.exception.UserAlreadyExistException;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthenticationController {
    UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registration) {
        try {
            userService.registerUser(registration);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody login) {
        String jwt = userService.loginUser(login);
        if(jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/profile")
    public userSite getLoggedInProfile(@AuthenticationPrincipal userSite user) {
        return user;
    }

    @PostMapping("/profile/upload")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image selected.");
        }

        try {
            // Path to the public/images directory in the React project
            String reactImagesDirectory = "public/images"; // Relative path for React project

            // Ensure the directory exists
            File directory = new File(reactImagesDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the file with a unique name
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename(); // To avoid conflicts
            Path filePath = Paths.get(directory.getAbsolutePath(), fileName);
            Files.write(filePath, image.getBytes());

            // Return the relative URL for the React app to access
            String imageUrl = "/images/" + fileName; // Relative path to be used in the React app
            return ResponseEntity.ok().body("{\"imageUrl\":\"" + imageUrl + "\"}");

        } catch (IOException e) {
            e.printStackTrace(); // Print exception in logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }
}
