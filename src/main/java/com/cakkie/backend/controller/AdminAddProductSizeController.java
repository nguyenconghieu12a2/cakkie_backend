package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminAddProductSizeDTO;
import com.cakkie.backend.dto.AdminProductItemSizeDTO;
import com.cakkie.backend.exception.AdminProductNotFound;
import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.AdminProductRepository;
import com.cakkie.backend.service.AdminAddProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminAddProductSizeController {

    @Autowired
    private AdminAddProductSizeService adminAddProductSizeService;

    @Autowired
    private AdminProductRepository productRepo;  // Ensure this is injected

    private AdminAddProductSizeDTO convertToDTO(productItem productItem) {
        AdminAddProductSizeDTO dto = new AdminAddProductSizeDTO();
        dto.setProductId(productItem.getProId().getId());
        dto.setSize(productItem.getSize());
        dto.setPrice(productItem.getPrice());
        dto.setQtyInStock(productItem.getQtyInStock());
        dto.setIsDeleted(productItem.getIsDeleted());
        return dto;
    }

    @PostMapping("/add-size/{productId}")
    public ResponseEntity<?> addProductSize(@PathVariable("productId") int productId,
                                            @RequestBody AdminAddProductSizeDTO adminAddProductSizeDTO) {
        // Ensure the product exists
        product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new AdminProductNotFound("Product with ID " + productId + " not found"));

        // Check if the size already exists
        boolean sizeExists = existingProduct.getProductItemList().stream()
                .anyMatch(item -> item.getSize().equalsIgnoreCase(adminAddProductSizeDTO.getSize()));

        if (sizeExists) {
            return ResponseEntity.badRequest().body("This size already exists for the selected product.");
        }

        // Create and save the new product size
        productItem newProductItem = new productItem();
        newProductItem.setProId(existingProduct);
        newProductItem.setSize(adminAddProductSizeDTO.getSize());
        newProductItem.setPrice(adminAddProductSizeDTO.getPrice());
        newProductItem.setQtyInStock(adminAddProductSizeDTO.getQtyInStock());
        newProductItem.setIsDeleted(1); // Default to active

        productItem savedProductItem = adminAddProductSizeService.addProductSize(newProductItem);
        AdminAddProductSizeDTO responseDTO = convertToDTO(savedProductItem);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/detail-size/{proId}")
    public ResponseEntity<List<AdminProductItemSizeDTO>> getProIdProItemIdSizeByProId(@PathVariable int proId) {
        List<AdminProductItemSizeDTO> productItemSizes = adminAddProductSizeService.getProIdProItemIdSizeByProId(proId);
        return ResponseEntity.ok(productItemSizes);
    }

    @PutMapping("/delete-size/{productItemId}")
    public ResponseEntity<?> deleteProductSize(@PathVariable int productItemId) {
        try {
            adminAddProductSizeService.deleteProductSize(productItemId);
            return ResponseEntity.ok("Product size deleted successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting product size: " + e.getMessage());
        }
    }
}
