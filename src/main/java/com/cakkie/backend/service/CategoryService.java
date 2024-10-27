package com.cakkie.backend.service;

import com.cakkie.backend.DTO.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    category getSubCateById(int id);
    List<CategoryDTO> getSubCategoriesByParentId(Integer parentId);
}
