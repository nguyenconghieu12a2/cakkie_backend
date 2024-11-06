package com.cakkie.backend.service;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization

import java.util.List;

public interface CategoryService {
    // Level1
    List<CategoryDTO> getAllCategories();
    category addCategory(category category);// Ensure capitalization
    category updateCategory(int id, category category);

    // Level2
    List<CategoryDTO> getSubCategoriesByParentId(Integer parentId);
    category addSubCategory(category category);
    category findCategoryById(Integer id);
    category updateSubCategory(int id, category category);

    // Level 3
    List<CategoryDTO> getSubSubCategoriesByParentId(Integer parentId);
    category addSubSubCategory(category category);
    category findSubCategoryById(Integer id);
}