package com.cakkie.backend.controller.adminProduct;


import com.cakkie.backend.dto.adminCategory.AdminCategoryDTO;
import com.cakkie.backend.dto.adminProduct.AdminProductDTO;
import com.cakkie.backend.dto.adminProduct.AdminProductInfoDTO;
import com.cakkie.backend.exception.adminProduct.AdminProductNotFound;
import com.cakkie.backend.model.product;
import com.cakkie.backend.repository.adminProduct.AdminProductDesTitleRepository;
import com.cakkie.backend.service.adminCategory.AdminCategoryService;
import com.cakkie.backend.service.adminProduct.AdminProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminProductController {
    private final AdminProductService adminProductServices;
    private final AdminCategoryService adminCategoryService;
    private final AdminProductDesTitleRepository adminProductDesTitleRepository;

    @GetMapping("/admin-product")
    public ResponseEntity<List<AdminProductDTO>> getAllProduct() {
        List<AdminProductDTO> products = adminProductServices.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<AdminProductDTO> getProductById(@PathVariable int id) {
        AdminProductDTO product = adminProductServices.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add/admin-product")
    public ResponseEntity<product> createProduct(
            @RequestParam("categoryId") int categoryId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productRating") int productRating,
            @RequestParam("isDelete") int isDelete,
            @RequestParam("size") String size,
            @RequestParam("qtyInStock") long qtyInStock,
            @RequestParam("price") long price
    ) {
        try {
            product addedProduct = adminProductServices.addProduct(
                    categoryId, name, description, productImage, productRating, isDelete,
                    size, qtyInStock, price);
            return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<product> updateProduct(
            @PathVariable int productId,
            @RequestParam(value = "categoryId", required = false) Integer categoryId, // Changed to Integer
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage,
            @RequestParam(value = "productRating", required = false) Integer productRating, // Changed to Integer
            @RequestParam(value = "isDelete", required = false) Integer isDelete, // Changed to Integer
            @RequestParam(value = "sizes", required = false) String sizesJson // Receive sizes as JSON string
    ) {
        try {
            if (categoryId == null) {
                throw new IllegalArgumentException("Category ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }

            List<Map<String, Object>> sizes = null;
            if (sizesJson != null && !sizesJson.trim().isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                sizes = objectMapper.readValue(sizesJson, List.class);
            }

            product updatedProduct = adminProductServices.updateProduct(
                    productId, categoryId, name, description, productImage,
                    productRating != null ? productRating : 0, // Default value if null
                    isDelete != null ? isDelete : 1, // Default value if null
                    sizes
            );

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/desTitles")
    public ResponseEntity<List<String>> getAllDesTitles() {
        List<String> titles = adminProductServices.getAllDesTitles();
        return ResponseEntity.ok(titles);
    }

    //Description Information
    //Create Information
    @PostMapping("/product/add-desinfo/{productId}")
    public ResponseEntity<String> addNewDesInfo(
            @PathVariable int productId,
            @RequestBody AdminProductInfoDTO productInfo) {
        try {
            if (productInfo.getDesTitleID() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Title ID: Title ID must be greater than 0");
            }

            // Call the service method to add the new product description using the custom insert query
            adminProductServices.addNewDesInfoUsingInsertQuery(
                    productId, productInfo.getDesTitleID(), productInfo.getDesInfo(), productInfo.getIsDelete());

            return ResponseEntity.status(HttpStatus.CREATED).body("Product description added successfully using custom query");
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Update Des Info
    @PutMapping("/product/update-desinfo/{productId}")
    public ResponseEntity<String> updateProductDesInfo(
            @PathVariable int productId,
            @RequestBody Map<String, Object> requestBody) {
        try {
            int desTitleId = (int) requestBody.get("desTitleId");
            String desInfo = (String) requestBody.get("desInfo");

            adminProductServices.updateProductDesInfo(productId, desTitleId, desInfo);
            return ResponseEntity.status(HttpStatus.OK).body("Product description updated successfully");
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Delete Des Info
    @DeleteMapping("/product/delete-desinfo/{productId}")
    public ResponseEntity<String> deleteProductDesInfo(
            @PathVariable int productId,
            @RequestBody Map<String, Object> requestBody
    ) {
        try {
            int desTitleId = (int) requestBody.get("desTitleId");
            adminProductServices.deleteProductDesInfo(productId, desTitleId);
            return ResponseEntity.status(HttpStatus.OK).body("Product description deleted successfully");
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<product> deleteProduct(@PathVariable int id) {
        try {
            product deletedProduct = adminProductServices.deleteProduct(id);
            return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/product-recovery/{id}")
    public ResponseEntity<product> recoveryProduct(@PathVariable int id) {
        try {
            product recoveryProduct = adminProductServices.recoverProduct(id);
            return new ResponseEntity<>(recoveryProduct, HttpStatus.OK);
        } catch (AdminProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<AdminCategoryDTO>> getAllSubSubCategories() {
        List<AdminCategoryDTO> categories = adminCategoryService.getAllSubSubCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<String>> getAllSizes() {
        List<String> sizes = adminProductServices.getAllSize();
        return ResponseEntity.ok(sizes);
    }

    //View All Deleted Product
    @GetMapping("/admin-product/deleted")
    public ResponseEntity<List<AdminProductDTO>> getAllDeletedProduct() {
        List<AdminProductDTO> products = adminProductServices.getAllDeletedProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/admin-product/category-not-deleted")
    public ResponseEntity<List<AdminProductDTO>> getAllCategoryNotDeletedProduct() {
        List<AdminProductDTO> products = adminProductServices.getCategryDeleteProduct();
        return ResponseEntity.ok(products);
    }
}