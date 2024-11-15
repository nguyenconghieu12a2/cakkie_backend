package com.cakkie.backend.controller;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.exception.CategoryNotFound;
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
    @GetMapping("/api/category/{parentId}/sub-category")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable Integer parentId) {
        List<CategoryDTO> subCategories = categoryService.getSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @PostMapping("/api/category/{parentId}/sub-category")
    public ResponseEntity<CategoryDTO> createSubCategory(@PathVariable Integer parentId, @RequestBody CategoryDTO subCategoryDTO) {
        category parentCategory = categoryService.findCategoryById(parentId); // Find the parent category

        category subCategory = new category();
        subCategory.setCateName(subCategoryDTO.getCateName());
        subCategory.setIsDeleted(subCategoryDTO.getIsDeleted());
        subCategory.setParentId(parentCategory); // Set the parent category

        category savedCategory = categoryService.addSubCategory(parentId, subCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }

    //Delete Level2
    @GetMapping("/api/view-deleted/sub-category")
    public ResponseEntity<List<CategoryDTO>> getDeletedSubCategories() {
        List<CategoryDTO> deletedSubCate = categoryService.getAllDeletedSubCategories();
        return new ResponseEntity<>(deletedSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/api/category/{id}/sub-category")
    public ResponseEntity<String> deleteSubCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (CategoryNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subcategory not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //Level 3
    @GetMapping("/api/category/sub-category/{parentId}/sub-sub-category")
    public ResponseEntity<List<CategoryDTO>> getSubCategoriesByParentId(@PathVariable Integer parentId) {
        List<CategoryDTO> subSubCategories = categoryService.getSubSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subSubCategories, HttpStatus.OK);
    }
    
    @PostMapping("/api/category/sub-category/{parentId}/sub-sub-category")
    public ResponseEntity<CategoryDTO> createSubSubCategory(@PathVariable Integer parentId, @RequestBody CategoryDTO subSubCategoryDTO) {
        category parentSubCategory = categoryService.findCategoryById(parentId);

        category subSubCategory = new category();
        subSubCategory.setCateName(subSubCategoryDTO.getCateName());
        subSubCategory.setIsDeleted(subSubCategoryDTO.getIsDeleted());
        subSubCategory.setParentId(parentSubCategory);

        category savedCategory = categoryService.addSubSubCategory(parentId, subSubCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }

    @GetMapping("/api/sub-sub-category")
    public ResponseEntity<List<CategoryDTO>> getAllSubSubCategories() {
        List<CategoryDTO> subSubCate = categoryService.getAllSubSubCategory();
        return new ResponseEntity<>(subSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/delete/sub-sub-category/{id}")
    public ResponseEntity<String> deleteSubSubCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteSubSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //View Deleted
    //Level 3
    @GetMapping("/api/view-deleted/sub-sub-category")
    public ResponseEntity<List<CategoryDTO>> getDeletedSubSubCategories() {
        List<CategoryDTO> deletedSubSubCate = categoryService.getAllDeletedSubSubCategories();
        return new ResponseEntity<>(deletedSubSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/api/recover/sub-sub-category/{id}")
    public ResponseEntity<String> recoverSubSubCategory(@PathVariable Integer id) {
        try {
            categoryService.recoverSubSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //Get Null Sub
    @GetMapping("/api/admin/null-sub-subCate")
    public ResponseEntity<List<CategoryDTO>> getNullSubSubCategories() {
        List<CategoryDTO> nullCate = categoryService.getNullSubSubCategory();
        return new ResponseEntity<>(nullCate, HttpStatus.OK);
    }
}
