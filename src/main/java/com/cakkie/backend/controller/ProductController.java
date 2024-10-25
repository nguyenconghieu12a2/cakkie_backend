package com.cakkie.backend.controller;


import com.cakkie.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
//    @Autowired
//    private ProductService productServices;
//
//    @PostMapping("/getAllProduct")
//    public ResponseEntity<?> getAllProduct() {
//        return new ResponseEntity<>(productServices.getAllProduct(), HttpStatus.OK);
//    }
}
