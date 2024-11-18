package com.cakkie.backend.service;

import com.cakkie.backend.dto.CategoryDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.model.category;
import com.cakkie.backend.model.product;
import com.cakkie.backend.repository.CategoryRepository;
import com.cakkie.backend.repository.ProductRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Override
    @Transactional
    public void deleteCategory(int id) {
        // Fetch the main category
        category mainCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category not found with ID: " + id));

        // Fetch all subCategories and subSubCategories under the mainCategory
        List<category> subCategories = categoryRepository.findAllSubSubCategoriesByParentId(id);
        for (category subCategory : subCategories) {
            List<category> subSubCategories = categoryRepository.findAllSubSubCategoriesByParentId(subCategory.getId());

            // Check if subSubCategories are all deleted
            boolean allSubSubCategoriesDeleted = subSubCategories.stream()
                    .allMatch(subSubCate -> subSubCate.getIsDeleted() == 0);

            // If any subSubCategory is not deleted, throw an exception
            if (!allSubSubCategoriesDeleted) {
                throw new IllegalStateException("Cannot delete main category because it contains active sub-subcategories.");
            }

            // Check if the subCategory itself is deleted
            if (subCategory.getIsDeleted() != 0) {
                throw new IllegalStateException("Cannot delete main category because it contains active subcategories.");
            }

            // Mark all subSubCategories as deleted
            for (category subSubCategory : subSubCategories) {
                subSubCategory.setIsDeleted(0);
                categoryRepository.save(subSubCategory);

                // Mark all products under subSubCategory as deleted
                List<product> productsInSubSubCategory = productRepository.findByCategoryID(subSubCategory);
                for (product product : productsInSubSubCategory) {
                    product.setIsDeleted(0);
                    productRepository.save(product);
                }
            }
        }

        // Mark the mainCategory as deleted
        mainCategory.setIsDeleted(0);
        categoryRepository.save(mainCategory);

        // Mark all products directly associated with the mainCategory as deleted
        List<product> productsInMainCategory = productRepository.findByCategoryID(mainCategory);
        for (product product : productsInMainCategory) {
            product.setIsDeleted(0);
            productRepository.save(product);
        }
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

        List<category> subSubCategories = categoryRepository.findAllSubSubCategoriesByParentId(id);

        if (!subSubCategories.stream().anyMatch(subSubCate -> subSubCate.getIsDeleted() == 0)) {
            throw new IllegalStateException("Cannot delete sub-category because it contains sub-subcategories.");
        }

        subCategory.setIsDeleted(0);
        categoryRepository.save(subCategory);

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

        List<product> associatedProducts = productRepository.findByCategoryID(subCategory);

        boolean hasStock = associatedProducts.stream()
                        .flatMap(product -> product.getProductItemList().stream())
                                .anyMatch(productItem -> productItem.getQtyInStock() > 0);

        if (hasStock) {
            throw new IllegalStateException("Cannot delete subcategory because some product items still have stock.");
        }

        subCategory.setIsDeleted(0);
        categoryRepository.save(subCategory);


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
        category subSubCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Subcategory not found with ID: " + id));

        category parentSubCategory = subSubCategory.getParentId();

        if (parentSubCategory.getIsDeleted() != 1) {
            throw new IllegalStateException("Cannot recover sub-subcategory because the parent subcategory is not deleted.");
        }

        subSubCategory.setIsDeleted(1);
        categoryRepository.save(subSubCategory);

        List<product> associatedProducts = productRepository.findByCategoryID(subSubCategory);
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

    @Override
    public List<CategoryDTO> getFullDeletedCategories() {
        List<Object[]> fullDeleteCate = categoryRepository.getFullDeleteCategories();
        List<CategoryDTO> result = new ArrayList<>();

        for (Object[] row : fullDeleteCate) {
            int mainId = (Integer) row[0];
            String mainName = (String) row[1];
            int subId = (Integer) row[2];
            String subName = (String) row[3];
            int subSubId = (Integer) row[4];
            String subSubName = (String) row[5];
            int isDeleted = (Integer) row[6];

            CategoryDTO categoryDTO = new CategoryDTO(
                    mainId, mainName, subId, subName, subSubId, subSubName, isDeleted
            );
            result.add(categoryDTO);
        }
        return result;
    }

    @Override
    public List<CategoryDTO> getFullSubDeletedCategories() {
        List<Object[]> fullSubDeletedCate = categoryRepository.getFullDeleteSubCategories();
        List<CategoryDTO> result = new ArrayList<>();

        for (Object[] row : fullSubDeletedCate) {
            int subId = (Integer) row[0];
            String subName = (String) row[1];
            int subSubId = (Integer) row[2];
            String subSubName = (String) row[3];
            int isDeleted = (Integer) row[4];

            CategoryDTO categoryDTO = new CategoryDTO(
                    subId, subName, subSubId, subSubName, isDeleted
            );
            result.add(categoryDTO);
        }
        return result;
    }


}
