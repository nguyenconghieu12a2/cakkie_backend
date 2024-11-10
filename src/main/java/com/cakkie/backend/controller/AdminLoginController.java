package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminLoginAPI;
import com.cakkie.backend.dto.AdminLoginResponse;
import com.cakkie.backend.model.admin;
import com.cakkie.backend.service.AdminLoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminLoginController {
    AdminLoginService adminLoginService;
    public AdminLoginController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/admin-login")
    public ResponseEntity<AdminLoginResponse> loginUser(@Valid @RequestBody AdminLoginAPI login) {
        String jwt = adminLoginService.loginAdmin(login);
        Optional<admin> opAdmin = adminLoginService.findByUsernameIgnoreCase(login.getUsername());
        if(jwt == null || opAdmin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            AdminLoginResponse response = new AdminLoginResponse();
            response.setJwtAdmin(jwt);
            response.setId(opAdmin.get().getId());
            response.setImage(opAdmin.get().getAvatarImg());
            return ResponseEntity.ok(response);
        }
    }
}
