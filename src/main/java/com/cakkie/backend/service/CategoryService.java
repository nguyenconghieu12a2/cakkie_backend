package com.cakkie.backend.service;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization

import java.util.List;

public interface CategoryService {
    // Level1
    List<CategoryDTO> getAllCategories();
    category addCategory(category category);// Ensure capitalization
    category updateCategory(int id, category category);
    List<CategoryDTO> getAllDeletedCategories();
    void deleteCategory(int id);

    // Level2
    List<CategoryDTO> getSubCategoriesByParentId(Integer parentId);
    category addSubCategory(Integer parentId, category category);
    category findCategoryById(Integer id);
    category updateSubCategory(int id, category category);
    List<CategoryDTO> getAllDeletedSubCategories();
    void deleteSubCategory(Integer id);

    // Level 3
    List<CategoryDTO> getSubSubCategoriesByParentId(Integer parentId);
    category addSubSubCategory(Integer parentId, category category);
    category findSubCategoryById(Integer id);
    List<CategoryDTO> getAllSubSubCategory();
    void deleteSubSubCategory(Integer id);
    List<CategoryDTO> getAllDeletedSubSubCategories();
    void recoverSubSubCategory(Integer id);

    //Get Null SubCat
    List<CategoryDTO> getNullSubSubCategory();

    //Get Full Deleted
    List<CategoryDTO> getFullDeletedCategories();

    //Get Full SubDeleted
    List<CategoryDTO> getFullSubDeletedCategories();
}