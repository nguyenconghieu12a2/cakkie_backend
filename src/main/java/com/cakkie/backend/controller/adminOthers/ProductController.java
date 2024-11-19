package com.cakkie.backend.controller.adminOthers;


import com.cakkie.backend.dto.product.ProductDTO;
import com.cakkie.backend.service.adminOthers.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productServices;

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productServices.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
