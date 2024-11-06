package com.cakkie.backend.service;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.model.category; // Ensure capitalization
import com.cakkie.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImplement implements CategoryService {

    private final CategoryRepository categoryRepository;

    //Level 1
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.getAllMainCategory();
    }

    @Override
    public category addCategory(category category) {
        return categoryRepository.save(category);
    }

    @Override
    public category updateCategory(int id, category category) {
        category existCate = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category not found with ID: " + id));
        existCate.setCateName(category.getCateName());
        return categoryRepository.save(existCate);
    }

    //Level 2
    @Override
    public List<CategoryDTO> getSubCategoriesByParentId(Integer parentId) {
        return categoryRepository.getSubCategoriesByParentId(parentId);
    }

    @Override
    public category addSubCategory(category category) {
        return categoryRepository.save(category);
    }

    @Override
    public category findCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category not found with ID: " + id));
    }

    @Override
    public category updateSubCategory(int id, category category) {
        category existSubCate = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category not found with ID: " + id));
        existSubCate.setCateName(category.getCateName());
        return categoryRepository.save(existSubCate);
    }

    //Level 3
    @Override
    public List<CategoryDTO> getSubSubCategoriesByParentId(Integer parentId) {
        return categoryRepository.getSubSubCategoriesByParentId(parentId);
    }

    @Override
    public category findSubCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category not found with ID: " + id));
    }

    @Override
    public category addSubSubCategory(category category) {
        return categoryRepository.save(category);
    }


}
