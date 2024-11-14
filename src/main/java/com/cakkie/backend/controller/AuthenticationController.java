package com.cakkie.backend.controller;

import com.cakkie.backend.dto.LoginBody;
import com.cakkie.backend.dto.LoginResponse;
import com.cakkie.backend.dto.RegistrationBody;
import com.cakkie.backend.exception.UserAlreadyExistException;
import com.cakkie.backend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
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

    @PostMapping("/password-reset-request")
    public ResponseEntity<String> sendPasswordResetEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found or unable to send reset email.");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        logger.debug("Verifying OTP for email: {} with OTP: {}", email, otp);
        try {
            // Verify OTP and generate reset password link
            String resetLink = userService.verifyOtpAndGenerateResetLink(email, otp);
            logger.debug("Generated reset link: {}", resetLink);
            return ResponseEntity.ok(resetLink); // Return reset link to frontend
        } catch (ResponseStatusException e) {
            logger.error("OTP verification failed for email: {} - Reason: {}", email, e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<String> validateResetToken(@RequestParam String email, @RequestParam String token) {
        logger.debug("Validating reset token for email: {} with token: {}", email, token);
        boolean isValid = userService.validateResetToken(email, token);
        if (isValid) {
            return ResponseEntity.ok("Valid token.");
        } else {
            logger.warn("Invalid or expired token for email: {}", email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired reset token.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        logger.debug("Attempting to reset password for email: {}", email);
        try {
            boolean isTokenValid = userService.validateResetToken(email, token);
            if (!isTokenValid) {
                logger.warn("Invalid or expired token for password reset - email: {}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
            }

            userService.updatePassword(email, newPassword);
            logger.info("Password reset successfully for email: {}", email);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (Exception e) {
            logger.error("Failed to reset password for email: {} - Error: {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password.");
        }
    }
}
