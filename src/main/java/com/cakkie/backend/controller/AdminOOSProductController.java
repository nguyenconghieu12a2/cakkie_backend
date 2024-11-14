package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminOOSProductDTO;
import com.cakkie.backend.service.AdminOOSProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminOOSProductController {
    private final AdminOOSProductService adminOOSProductService;

    @GetMapping("/oos-products")
    public List<AdminOOSProductDTO> getAllOOSProducts() {
        return adminOOSProductService.getOOSProducts();
    }
}
