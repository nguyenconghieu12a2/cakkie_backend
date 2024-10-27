package com.cakkie.backend.repository;

import com.cakkie.backend.DTO.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<category, Integer> {

    // Get all main categories
    @Query("SELECT new com.cakkie.backend.DTO.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId IS NULL AND c.isDeleted = 1")
    List<CategoryDTO> getAllMainCategory();

    // Get subcategories by parent ID
    @Query("SELECT new com.cakkie.backend.DTO.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId = ?1 AND c.isDeleted = 1")
    List<CategoryDTO> getSubCategoriesByParentId(Integer parentId);
}
