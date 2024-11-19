package com.cakkie.backend.controller;

import com.cakkie.backend.dto.AdminCategoryDTO;
import com.cakkie.backend.exception.AdminCategoryNotFound;
import com.cakkie.backend.model.category;
import com.cakkie.backend.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    private AdminCategoryDTO convertToDTO(category category) {
        AdminCategoryDTO dto = new AdminCategoryDTO();
        dto.setId(category.getId());
        dto.setCateName(category.getCateName());
        dto.setParentId(category.getParentId() != null ? category.getParentId().getId() : null);
        dto.setIsDeleted(category.getIsDeleted());
        return dto;
    }

    private category convertToEntity(AdminCategoryDTO adminCategoryDTO) {
        category category = new category();
        category.setId(adminCategoryDTO.getId());
        category.setCateName(adminCategoryDTO.getCateName());
        category.setIsDeleted(adminCategoryDTO.getIsDeleted());
        if (adminCategoryDTO.getParentId() != null) {
            category parentCategory = adminCategoryService.findCategoryById(adminCategoryDTO.getParentId());
            category.setParentId(parentCategory);
        }
        return category;
    }

    //Level1
    @GetMapping("/category")
    public ResponseEntity<List<AdminCategoryDTO>> getAllCategories() {
        List<AdminCategoryDTO> categories = adminCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public ResponseEntity<AdminCategoryDTO> createCategory(@RequestBody category category) {
        category saveCategory = adminCategoryService.addCategory(category);
        return new ResponseEntity<>(convertToDTO(saveCategory), HttpStatus.CREATED);
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<AdminCategoryDTO> updateCategory(@PathVariable int id, @RequestBody AdminCategoryDTO adminCategoryDTO) {
        category updatedCategoryEntity = convertToEntity(adminCategoryDTO);
        category updatedCategory = adminCategoryService.updateCategory(id, updatedCategoryEntity);
        return new ResponseEntity<>(convertToDTO(updatedCategory), HttpStatus.OK);
    }

    //Level2
    @GetMapping("/category/sub-category/{parentId}")
    public ResponseEntity<List<AdminCategoryDTO>> getSubCategories(@PathVariable Integer parentId) {
        List<AdminCategoryDTO> subCategories = adminCategoryService.getSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @PostMapping("/category/add-sub-category/{parentId}")
    public ResponseEntity<AdminCategoryDTO> createSubCategory(@PathVariable Integer parentId, @RequestBody AdminCategoryDTO subAdminCategoryDTO) {
        category parentCategory = adminCategoryService.findCategoryById(parentId); // Find the parent category

        category subCategory = new category();
        subCategory.setCateName(subAdminCategoryDTO.getCateName());
        subCategory.setIsDeleted(subAdminCategoryDTO.getIsDeleted());
        subCategory.setParentId(parentCategory); // Set the parent category

        category savedCategory = adminCategoryService.addSubCategory(parentId, subCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            adminCategoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete category because it has associated products.");
        } catch (AdminCategoryNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subcategory not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //Delete Level2
    @GetMapping("/api/view-deleted/sub-category")
    public ResponseEntity<List<AdminCategoryDTO>> getDeletedSubCategories() {
        List<AdminCategoryDTO> deletedSubCate = adminCategoryService.getAllDeletedSubCategories();
        return new ResponseEntity<>(deletedSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/delete/sub-category/{id}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable Integer id) {
        try {
            adminCategoryService.deleteSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (AdminCategoryNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subcategory not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //Level 3
    @GetMapping("/category/sub-category/sub-sub-category/{parentId}")
    public ResponseEntity<List<AdminCategoryDTO>> getSubCategoriesByParentId(@PathVariable Integer parentId) {
        List<AdminCategoryDTO> subSubCategories = adminCategoryService.getSubSubCategoriesByParentId(parentId);
        return new ResponseEntity<>(subSubCategories, HttpStatus.OK);
    }
    
    @PostMapping("/category/sub-category/add-sub-sub-category/{parentId}")
    public ResponseEntity<AdminCategoryDTO> createSubSubCategory(@PathVariable Integer parentId, @RequestBody AdminCategoryDTO subSubAdminCategoryDTO) {
        category parentSubCategory = adminCategoryService.findCategoryById(parentId);

        category subSubCategory = new category();
        subSubCategory.setCateName(subSubAdminCategoryDTO.getCateName());
        subSubCategory.setIsDeleted(subSubAdminCategoryDTO.getIsDeleted());
        subSubCategory.setParentId(parentSubCategory);

        category savedCategory = adminCategoryService.addSubSubCategory(parentId, subSubCategory);
        return new ResponseEntity<>(convertToDTO(savedCategory), HttpStatus.CREATED);
    }

    @GetMapping("/api/sub-sub-category")
    public ResponseEntity<List<AdminCategoryDTO>> getAllSubSubCategories() {
        List<AdminCategoryDTO> subSubCate = adminCategoryService.getAllSubSubCategory();
        return new ResponseEntity<>(subSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/delete/sub-sub-category/{id}")
    public ResponseEntity<String> deleteSubSubCategory(@PathVariable Integer id) {
        try {
            adminCategoryService.deleteSubSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //View Deleted
    //Level 3
    @GetMapping("/view-deleted/sub-sub-category")
    public ResponseEntity<List<AdminCategoryDTO>> getDeletedSubSubCategories() {
        List<AdminCategoryDTO> deletedSubSubCate = adminCategoryService.getAllDeletedSubSubCategories();
        return new ResponseEntity<>(deletedSubSubCate, HttpStatus.OK);
    }

    @DeleteMapping("/recover/sub-sub-category/{id}")
    public ResponseEntity<String> recoverSubSubCategory(@PathVariable Integer id) {
        try {
            adminCategoryService.recoverSubSubCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete subcategory because it has associated products.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the subcategory.");
        }
    }

    //Get Null Sub
    @GetMapping("/null-sub-subCate")
    public ResponseEntity<List<AdminCategoryDTO>> getNullSubSubCategories() {
        List<AdminCategoryDTO> nullCate = adminCategoryService.getNullSubSubCategory();
        return new ResponseEntity<>(nullCate, HttpStatus.OK);
    }

    //Get Full Deleted Cate
    @GetMapping("/full-deleted-categories")
    public ResponseEntity<List<AdminCategoryDTO>> getFullDeletedCategories() {
        List<AdminCategoryDTO> fullDeletedCategories = adminCategoryService.getFullDeletedCategories();
        return new ResponseEntity<>(fullDeletedCategories, HttpStatus.OK);
    }

    //Get Full Deleted SubCate
    @GetMapping("/full-sub-deleted")
    public ResponseEntity<List<AdminCategoryDTO>> getFullSubDeletedCategories() {
        List<AdminCategoryDTO> fullSubDeletedCategories = adminCategoryService.getFullSubDeletedCategories();
        return new ResponseEntity<>(fullSubDeletedCategories, HttpStatus.OK);
    }

}
