package com.cakkie.backend.controller.adminProduct;

import com.cakkie.backend.dto.adminProduct.AdminOOSProductDTO;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.service.adminProduct.AdminOOSPProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminOOSProductController {
    private final AdminOOSPProductService adminOOSProductService;

    @GetMapping("/oos-products")
    public List<AdminOOSProductDTO> getAllOOSProducts() {
        return adminOOSProductService.getOOSProducts();
    }

    @PutMapping("/oos-products/update/{id}")
    public ResponseEntity<productItem> updateOOSProduct(
            @PathVariable("id") int id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            int quantity = requestBody.get("quantity");
            productItem updatedProduct = adminOOSProductService.updateQuantity(id, quantity);
            return ResponseEntity.ok().body(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
