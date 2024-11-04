package com.cakkie.backend.controller;

import com.cakkie.backend.dto.ChangePasswordBody;
import com.cakkie.backend.dto.EditBody;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProfileController {
    UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public userSite getLoggedInProfile(@AuthenticationPrincipal userSite user) {
        return user;
    }

    @PutMapping("/edit-account")
    public ResponseEntity editAccount(@Valid @RequestBody EditBody editBody) {
        userService.updateUser(editBody);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordBody request) {
        boolean success = userService.changePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }
    }
}
