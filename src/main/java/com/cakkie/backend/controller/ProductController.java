package com.cakkie.backend.controller;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.product;
import com.cakkie.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    private ProductDTO coverToDto(product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCateName(product.getCategoryID().getCateName());
        productDTO.setName(product.getName());
        productDTO.setProductImage(product.getProductImage());
        productDTO.setQtyInStock(product.getProductItemList().getFirst().getQtyInStock());
        productDTO.setPrice(product.getProductItemList().getFirst().getPrice());
        productDTO.setSize(product.getProductItemList().getFirst().getSize());
        productDTO.setProductItemImage(product.getProductItemList().getFirst().getProductImage());
        productDTO.setIsDeleted(product.getIsDeleted());
        return productDTO;
    }

    @GetMapping("/api/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> product = productService.getAllProducts();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
