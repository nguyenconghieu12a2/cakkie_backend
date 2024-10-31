package com.cakkie.backend.controller;
import com.cakkie.backend.api.AdminLoginAPI;
import com.cakkie.backend.api.LoginResponse;
import com.cakkie.backend.service.AdminLoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AdminLoginController {
    AdminLoginService adminLoginService;
    public AdminLoginController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/admin-login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody AdminLoginAPI login) {
        String jwt = adminLoginService.loginAdmin(login);
        if(jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }
}
