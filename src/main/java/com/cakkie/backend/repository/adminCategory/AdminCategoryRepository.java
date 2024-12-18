package com.cakkie.backend.repository.adminCategory;

import com.cakkie.backend.dto.adminCategory.AdminCategoryDTO;
import com.cakkie.backend.model.category; // Ensure capitalization
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminCategoryRepository extends JpaRepository<category, Integer> {

    // Get all main categories
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId IS NULL AND c.isDeleted = 1")
    List<AdminCategoryDTO> getAllMainCategory();

    // Get subcategories by parent ID
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c1.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "WHERE c1.id = :parentId AND c.isDeleted = 1")
    List<AdminCategoryDTO> getSubCategoriesByParentId(@Param("parentId") Integer parentId);

    // Get subsubcate by parent ID
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c1.id = :parentId AND c.isDeleted = 1")
    List<AdminCategoryDTO> getSubSubCategoriesByParentId(@Param("parentId") Integer parentId);

    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c.isDeleted = 1")
    List<AdminCategoryDTO> getAllSubSubCategories();

    @Query("SELECT c FROM category c WHERE c.parentId.id = :parentId")
    List<category> findAllSubSubCategoriesByParentId(@Param("parentId") Integer parentId);

    //Delete Category
    //Level 1:
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c WHERE c.parentId IS NULL AND c.isDeleted = 0")
    List<AdminCategoryDTO> getAllDeletedMainCategory();

    //Level 2:
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c1.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "WHERE c.isDeleted = 0")
    List<AdminCategoryDTO> getAllDeletedSubCate();

    //Level 3:
    @Query("SELECT new com.cakkie.backend.dto.adminCategory.AdminCategoryDTO(c.id, c.cateName, c.parentId.id, c.isDeleted) " +
            "FROM category c " +
            "JOIN c.parentId c1 " +
            "JOIN c1.parentId c2 " +
            "WHERE c.isDeleted = 0")
    List<AdminCategoryDTO> getAllDeletedSubSubCategories();

    //SubSubCategory is Null
    @Query(value = "SELECT DISTINCT c1.cate_name, c1.id, c.is_deleted\n" +
            "FROM category c\n" +
            "JOIN category c1 ON c.parent_id = c1.id\n" +
            "JOIN category c2 ON c1.parent_id = c2.id\n" +
            "WHERE c.is_deleted = 0", nativeQuery = true)
    List<Object[]> getAllNullSubCategories();

    //Get Full Cate Deleted
    @Query (value = "SELECT \n" +
            "    c2.id AS c2_id, \n" +
            "    c2.cate_name AS c2_cate_name, \n" +
            "    c1.parent_id AS c1_parent_id, \n" +
            "    c1.cate_name AS c1_cate_name, \n" +
            "    c.parent_id AS c_parent_id, \n" +
            "    c.cate_name AS c_cate_name, \n" +
            "    c.is_deleted\n" +
            "FROM \n" +
            "    category c\n" +
            "JOIN \n" +
            "    category c1 ON c.parent_id = c1.id\n" +
            "JOIN \n" +
            "    category c2 ON c1.parent_id = c2.id\n" +
            "WHERE \n" +
            "    c1.is_deleted = 0 AND c.is_deleted = 0 AND c2.is_deleted = 0 ", nativeQuery = true)
    List<Object[]> getFullDeleteCategories();

    //Get SubCate and SubSubCate
    @Query(value = "SELECT c1.id, c1.cate_name, c.id, c.cate_name, c1.is_deleted\n" +
            "FROM category c\n" +
            "JOIN category c1 ON c.parent_id = c1.id\n" +
            "JOIN category c2 ON c1.parent_id = c2.id\n" +
            "WHERE c1.is_deleted = 0 ", nativeQuery = true)
    List<Object[]> getFullDeleteSubCategories();
}
