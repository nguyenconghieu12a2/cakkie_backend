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
            "WHERE c1.id = :parentId AND c.isDeleted = 1")
    List<CategoryDTO> getSubCategoriesByParentId(@Param("parentId") Integer parentId);

    // Get subsubcate by parent ID
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c1.id = :parentId AND c.isDeleted = 1")
    List<CategoryDTO> getSubSubCategoriesByParentId(@Param("parentId") Integer parentId);

    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c.isDeleted = 1")
    List<CategoryDTO> getAllSubSubCategories();

    @Query("SELECT c FROM category c WHERE c.parentId.id = :parentId")
    List<category> findAllSubSubCategoriesByParentId(@Param("parentId") Integer parentId);

    //Delete Category
    //Level 1:
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId IS NULL AND c.isDeleted = 0")
    List<CategoryDTO> getAllDeletedMainCategory();

    //Level 2:
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c1.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "WHERE c.isDeleted = 0")
    List<CategoryDTO> getAllDeletedSubCate();

    //Level 3:
    @Query("SELECT new com.cakkie.backend.dto.CategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c.isDeleted = 0")
    List<CategoryDTO> getAllDeletedSubSubCategories();
}
