package com.cakkie.backend.service;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.model.category; // Ensure capitalization
import com.cakkie.backend.model.product;
import com.cakkie.backend.repository.CategoryRepository;
import com.cakkie.backend.repository.ProductRepository;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryImplement implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

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

    @Override
    public List<CategoryDTO> getAllDeletedCategories() {
        return categoryRepository.getAllDeletedMainCategory();
    }

    //Level 2
    @Override
    public List<CategoryDTO> getSubCategoriesByParentId(Integer parentId) {
        return categoryRepository.getSubCategoriesByParentId(parentId);
    }

    @Override
    public category addSubCategory(Integer parentId, category subCategory) {
        category parentCategory = findCategoryById(parentId);
        subCategory.setParentId(parentCategory);
        return categoryRepository.save(subCategory);
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

    @Override
    public List<CategoryDTO> getAllDeletedSubCategories() {
        return categoryRepository.getAllDeletedSubCate();
    }

    @Override
    @Transactional
    public void deleteSubCategory(Integer id) {
        category subCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Subcategory not found with ID: " + id));

        subCategory.setIsDeleted(0);
        categoryRepository.save(subCategory);

        List<category> subSubCategories = categoryRepository.findAllSubSubCategoriesByParentId(id);
        for (category subSubCate : subSubCategories) {
            subSubCate.setIsDeleted(0);
            categoryRepository.save(subSubCate);

            List<product> productsInSubSubCate = productRepository.findByCategoryID(subSubCate);
            for (product prod : productsInSubSubCate) {
                prod.setIsDeleted(0);
                productRepository.save(prod);
            }
        }

        List<product> associatedProducts = productRepository.findByCategoryID(subCategory);
        for (product prod : associatedProducts) {
            prod.setIsDeleted(0);
            productRepository.save(prod);
        }
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
    public List<CategoryDTO> getAllSubSubCategory() {
        return categoryRepository.getAllSubSubCategories();
    }

    @Override
    @Transactional
    public void deleteSubSubCategory(Integer id) {
        category subCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Subcategory not found with ID: " + id));

        subCategory.setIsDeleted(0);
        categoryRepository.save(subCategory);

        List<product> associatedProducts = productRepository.findByCategoryID(subCategory);
        for (product prod : associatedProducts) {
            prod.setIsDeleted(0);
            productRepository.save(prod);
        }
    }

    @Override
    public List<CategoryDTO> getAllDeletedSubSubCategories() {
        return categoryRepository.getAllDeletedSubSubCategories();
    }

    @Override
    public void recoverSubSubCategory(Integer id) {
        category subCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Subcategory not found with ID: " + id));

        subCategory.setIsDeleted(1);
        categoryRepository.save(subCategory);

        List<product> associatedProducts = productRepository.findByCategoryID(subCategory);
        for (product prod : associatedProducts) {
            prod.setIsDeleted(1);
            productRepository.save(prod);
        }
    }

    @Override
    public category addSubSubCategory(Integer parentId, category subSubCategory) {
        category parentCate = findSubCategoryById(parentId);
        subSubCategory.setParentId(parentCate);
        return categoryRepository.save(subSubCategory);
    }

    //Get Null Sub Cate
    @Override
    public List<CategoryDTO> getNullSubSubCategory() {
        List<Object[]> cateNull = categoryRepository.getAllNullSubCategories();
        Map<Integer, CategoryDTO> categoryDTOMap = new HashMap<Integer, CategoryDTO>();

        for(Object[] row : cateNull) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCateName((String) row[0]);
            categoryDTO.setParentId((Integer) row[1]);
            categoryDTOMap.put((Integer) row[1], categoryDTO);
        }
        return new ArrayList<CategoryDTO>(categoryDTOMap.values());
    }
}
