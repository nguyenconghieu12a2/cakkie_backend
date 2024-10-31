package com.cakkie.backend.repository;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<category, Integer> {

    // Get all main categories
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId IS NULL AND c.isDeleted = 1")
    List<CategoryDTO> getAllMainCategory();

    // Get subcategories by parent ID
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c1.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "WHERE c1.id = :parentId AND c1.isDeleted = 1")
    List<CategoryDTO> getSubCategoriesByParentId(@Param("parentId") Integer parentId);

    // Get subsubcate by parent ID
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c1.id = :parentId AND c2.isDeleted = 1")
    List<CategoryDTO> getSubSubCategoriesByParentId(@Param("parentId") Integer parentId);

}
