package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization
import com.cakkie.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private category convertToEntity(CategoryDTO categoryDTO) {
        category category = new category();
        category.setId(categoryDTO.getId());
        category.setCateName(categoryDTO.getCateName());
        category.setIsDeleted(categoryDTO.getIsDeleted());
        if (categoryDTO.getParentId() != null) {
            category parentCategory = categoryService.findCategoryById(categoryDTO.getParentId());
            category.setParentId(parentCategory);
        }
        return category;
    }

    //Level1
    @GetMapping("/api/category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/api/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody category category) {
        category saveCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(convertToDTO(saveCategory), HttpStatus.CREATED);
    }

    @PutMapping("/api/category/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        category updatedCategoryEntity = convertToEntity(categoryDTO);
        category updatedCategory = categoryService.updateCategory(id, updatedCategoryEntity);
        return new ResponseEntity<>(convertToDTO(updatedCategory), HttpStatus.OK);
    }

    //Level2
    @GetMapping("/api/sub-category/{parentId}")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable Integer parentId) {
        List<CategoryDTO> subCategories = categoryService.getSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @PostMapping("api/sub-category/{parentId}")
    public ResponseEntity<CategoryDTO> createSubCategory(@PathVariable Integer parentId, @RequestBody category subCategory) {
        category parentCategory = categoryService.findCategoryById(parentId);
        subCategory.setParentId(parentCategory);
        category savedCategory = categoryService.addSubCategory(subCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }

    //Level 3
    @GetMapping("/api/category/sub-category/{parentId}")
    public ResponseEntity<List<CategoryDTO>> getSubCategoriesByParentId(@PathVariable Integer parentId) {
        List<CategoryDTO> subSubCategories = categoryService.getSubSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subSubCategories, HttpStatus.OK);
    }
    
    @PostMapping("/api/category/sub-category/{parentId}")
    public ResponseEntity<CategoryDTO> createSubCategoryByParentId(@PathVariable Integer parentId, @RequestBody category subSubCategory) {
        category parentCategory = categoryService.findSubCategoryById(parentId);
        subSubCategory.setParentId(parentCategory);
        category savedCategory = categoryService.addSubSubCategory(subSubCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }
}
