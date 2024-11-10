package com.cakkie.backend.controller;


import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductInfoDTO;
import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productDesInfo;
import com.cakkie.backend.model.productDesTitle;
import com.cakkie.backend.service.CategoryService;
import com.cakkie.backend.service.ProductService;
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
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productServices;
    private final CategoryService categoryService;

    @GetMapping("/admin-product")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productServices.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        ProductDTO product = productServices.getProductById(id);
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
            product addedProduct = productServices.addProduct(
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

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<product> updateProduct(
            @PathVariable int productId,
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
            product updatedProduct = productServices.updateProduct(
                    productId, categoryId, name, description, productImage, productRating, isDelete, size, qtyInStock, price
            );
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/product/{productId}/add-description")
    public ResponseEntity<productDesInfo> addDescriptionToProduct(
            @PathVariable int productId,
            @RequestBody ProductInfoDTO productInfo
    ) {
        try {
            productDesInfo addedDescription = productServices.addDescriptionToProduct(
                    productId, productInfo.getDesTitleID(), productInfo.getDesInfo(), productInfo.getIsDelete()
            );
            return new ResponseEntity<>(addedDescription, HttpStatus.CREATED);
        } catch (ProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<product> deleteProduct(@PathVariable int id) {
        try {
            product deletedProduct = productServices.deleteProduct(id);
            return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
        } catch (ProductNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllSubSubCategories() {
        List<CategoryDTO> categories = categoryService.getAllSubSubCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<String>> getAllSizes() {
        List<String> sizes = productServices.getAllSize();
        return ResponseEntity.ok(sizes);
    }

    //Deleted Product
    @GetMapping("/admin-product/deleted")
    public ResponseEntity<List<ProductDTO>> getAllDeletedProduct() {
        List<ProductDTO> products = productServices.getAllDeletedProduct();
        return ResponseEntity.ok(products);
    }

}