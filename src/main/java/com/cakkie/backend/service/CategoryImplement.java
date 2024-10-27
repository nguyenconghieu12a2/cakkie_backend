package com.cakkie.backend.service;

import com.cakkie.backend.DTO.CategoryDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.model.category; // Ensure capitalization
import com.cakkie.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImplement implements CategoryService {

    private final CategoryRepository categoryRepository; // Use constructor injection

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.getAllMainCategory(); // Ensure this method exists in your repository
    }

    @Override
    public category getSubCateById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Sorry, no sub-category with id: " + id));
    }

    @Override
    public List<CategoryDTO> getSubCategoriesByParentId(Integer parentId) {
        return categoryRepository.getSubCategoriesByParentId(parentId);
    }
}
