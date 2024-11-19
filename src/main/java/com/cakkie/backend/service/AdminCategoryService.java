package com.cakkie.backend.service;

import com.cakkie.backend.dto.AdminCategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization

import java.util.List;

public interface AdminCategoryService {
    // Level1
    List<AdminCategoryDTO> getAllCategories();
    category addCategory(category category);// Ensure capitalization
    category updateCategory(int id, category category);
    List<AdminCategoryDTO> getAllDeletedCategories();
    void deleteCategory(int id);

    // Level2
    List<AdminCategoryDTO> getSubCategoriesByParentId(Integer parentId);
    category addSubCategory(Integer parentId, category category);
    category findCategoryById(Integer id);
    category updateSubCategory(int id, category category);
    List<AdminCategoryDTO> getAllDeletedSubCategories();
    void deleteSubCategory(Integer id);

    // Level 3
    List<AdminCategoryDTO> getSubSubCategoriesByParentId(Integer parentId);
    category addSubSubCategory(Integer parentId, category category);
    category findSubCategoryById(Integer id);
    List<AdminCategoryDTO> getAllSubSubCategory();
    void deleteSubSubCategory(Integer id);
    List<AdminCategoryDTO> getAllDeletedSubSubCategories();
    void recoverSubSubCategory(Integer id);

    //Get Null SubCat
    List<AdminCategoryDTO> getNullSubSubCategory();

    //Get Full Deleted
    List<AdminCategoryDTO> getFullDeletedCategories();

    //Get Full SubDeleted
    List<AdminCategoryDTO> getFullSubDeletedCategories();
}