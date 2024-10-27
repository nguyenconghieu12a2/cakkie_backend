package com.cakkie.backend.controller;

import com.cakkie.backend.DTO.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization
import com.cakkie.backend.model.category;
import com.cakkie.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    private CategoryDTO convertToDTO(category category) { // Ensure capitalization
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCateName(category.getCateName());
        dto.setParentId(category.getParentId() != null ? category.getParentId().getId() : null);
        dto.setIsDeleted(category.getIsDeleted());
        return dto;
    }

    @GetMapping("/api/category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/api/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        category category = categoryService.getSubCateById(id); // This is incorrect
        return new ResponseEntity<>(convertToDTO(category), HttpStatus.OK);
    }
}
