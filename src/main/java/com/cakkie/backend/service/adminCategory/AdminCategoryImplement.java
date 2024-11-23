package com.cakkie.backend.service.adminCategory;

import com.cakkie.backend.dto.adminCategory.AdminCategoryDTO;
import com.cakkie.backend.exception.adminCategory.AdminCategoryNotFound;
import com.cakkie.backend.model.category;
import com.cakkie.backend.model.product;
import com.cakkie.backend.repository.adminCategory.AdminCategoryRepository;
import com.cakkie.backend.repository.adminProduct.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminCategoryImplement implements AdminCategoryService {

    private final AdminCategoryRepository adminCategoryRepository;
    private final AdminProductRepository adminProductRepository;

    //Level 1
    @Override
    public List<AdminCategoryDTO> getAllCategories() {
        return adminCategoryRepository.getAllMainCategory();
    }

    @Override
    public category addCategory(category category) {
        return adminCategoryRepository.save(category);
    }

    @Override
    public category updateCategory(int id, category category) {
        category existCate = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Category not found with ID: " + id));
        existCate.setCateName(category.getCateName());
        return adminCategoryRepository.save(existCate);
    }

    @Override
    public List<AdminCategoryDTO> getAllDeletedCategories() {
        return adminCategoryRepository.getAllDeletedMainCategory();
    }

    @Override
    @Transactional
    public void deleteCategory(int id) {
        // Fetch the main category
        category mainCategory = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Category not found with ID: " + id));

        // Fetch all subCategories and subSubCategories under the mainCategory
        List<category> subCategories = adminCategoryRepository.findAllSubSubCategoriesByParentId(id);
        for (category subCategory : subCategories) {
            List<category> subSubCategories = adminCategoryRepository.findAllSubSubCategoriesByParentId(subCategory.getId());

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
                adminCategoryRepository.save(subSubCategory);

                // Mark all products under subSubCategory as deleted
                List<product> productsInSubSubCategory = adminProductRepository.findByCategoryID(subSubCategory);
                for (product product : productsInSubSubCategory) {
                    product.setIsDeleted(0);
                    adminProductRepository.save(product);
                }
            }
        }

        // Mark the mainCategory as deleted
        mainCategory.setIsDeleted(0);
        adminCategoryRepository.save(mainCategory);

        // Mark all products directly associated with the mainCategory as deleted
        List<product> productsInMainCategory = adminProductRepository.findByCategoryID(mainCategory);
        for (product product : productsInMainCategory) {
            product.setIsDeleted(0);
            adminProductRepository.save(product);
        }
    }


    //Level 2
    @Override
    public List<AdminCategoryDTO> getSubCategoriesByParentId(Integer parentId) {
        return adminCategoryRepository.getSubCategoriesByParentId(parentId);
    }

    @Override
    public category addSubCategory(Integer parentId, category subCategory) {
        category parentCategory = findCategoryById(parentId);
        subCategory.setParentId(parentCategory);
        return adminCategoryRepository.save(subCategory);
    }

    @Override
    public category findCategoryById(Integer id) {
        return adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Category not found with ID: " + id));
    }

    @Override
    public category updateSubCategory(int id, category category) {
        category existSubCate = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Category not found with ID: " + id));
        existSubCate.setCateName(category.getCateName());
        return adminCategoryRepository.save(existSubCate);
    }

    @Override
    public List<AdminCategoryDTO> getAllDeletedSubCategories() {
        return adminCategoryRepository.getAllDeletedSubCate();
    }

    @Override
    @Transactional
    public void deleteSubCategory(Integer id) {
        category subCategory = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Subcategory not found with ID: " + id));

        List<category> subSubCategories = adminCategoryRepository.findAllSubSubCategoriesByParentId(id);

        if (!subSubCategories.stream().anyMatch(subSubCate -> subSubCate.getIsDeleted() == 0)) {
            throw new IllegalStateException("Cannot delete sub-category because it contains sub-subcategories.");
        }

        subCategory.setIsDeleted(0);
        adminCategoryRepository.save(subCategory);

        for (category subSubCate : subSubCategories) {
            subSubCate.setIsDeleted(0);
            adminCategoryRepository.save(subSubCate);

            List<product> productsInSubSubCate = adminProductRepository.findByCategoryID(subSubCate);
            for (product prod : productsInSubSubCate) {
                prod.setIsDeleted(0);
                adminProductRepository.save(prod);
            }
        }

        List<product> associatedProducts = adminProductRepository.findByCategoryID(subCategory);
        for (product prod : associatedProducts) {
            prod.setIsDeleted(0);
            adminProductRepository.save(prod);
        }
    }

    //Level 3
    @Override
    public List<AdminCategoryDTO> getSubSubCategoriesByParentId(Integer parentId) {
        return adminCategoryRepository.getSubSubCategoriesByParentId(parentId);
    }

    @Override
    public category findSubCategoryById(Integer id) {
        return adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Category not found with ID: " + id));
    }

    @Override
    public List<AdminCategoryDTO> getAllSubSubCategory() {
        return adminCategoryRepository.getAllSubSubCategories();
    }

    @Override
    @Transactional
    public void deleteSubSubCategory(Integer id) {
        category subCategory = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Subcategory not found with ID: " + id));

        List<product> associatedProducts = adminProductRepository.findByCategoryID(subCategory);

        boolean hasStock = associatedProducts.stream()
                        .flatMap(product -> product.getProductItemList().stream())
                                .anyMatch(productItem -> productItem.getQtyInStock() > 0);

        if (hasStock) {
            throw new IllegalStateException("Cannot delete subcategory because some product items still have stock.");
        }

        subCategory.setIsDeleted(0);
        adminCategoryRepository.save(subCategory);


        for (product prod : associatedProducts) {
            prod.setIsDeleted(0);
            adminProductRepository.save(prod);
        }
    }

    @Override
    public List<AdminCategoryDTO> getAllDeletedSubSubCategories() {
        return adminCategoryRepository.getAllDeletedSubSubCategories();
    }

    @Override
    public void recoverSubSubCategory(Integer id) {
        category subSubCategory = adminCategoryRepository.findById(id)
                .orElseThrow(() -> new AdminCategoryNotFound("Subcategory not found with ID: " + id));

        category parentSubCategory = subSubCategory.getParentId();

        if (parentSubCategory.getIsDeleted() != 1) {
            throw new IllegalStateException("Cannot recover sub-subcategory because the parent subcategory is not deleted.");
        }

        subSubCategory.setIsDeleted(1);
        adminCategoryRepository.save(subSubCategory);

        List<product> associatedProducts = adminProductRepository.findByCategoryID(subSubCategory);
        for (product prod : associatedProducts) {
            prod.setIsDeleted(1);
            adminProductRepository.save(prod);
        }
    }

    @Override
    public category addSubSubCategory(Integer parentId, category subSubCategory) {
        category parentCate = findSubCategoryById(parentId);
        subSubCategory.setParentId(parentCate);
        return adminCategoryRepository.save(subSubCategory);
    }

    //Get Null Sub Cate
    @Override
    public List<AdminCategoryDTO> getNullSubSubCategory() {
        List<Object[]> cateNull = adminCategoryRepository.getAllNullSubCategories();
        Map<Integer, AdminCategoryDTO> categoryDTOMap = new HashMap<Integer, AdminCategoryDTO>();

        for(Object[] row : cateNull) {
            AdminCategoryDTO adminCategoryDTO = new AdminCategoryDTO();
            adminCategoryDTO.setCateName((String) row[0]);
            adminCategoryDTO.setParentId((Integer) row[1]);
            categoryDTOMap.put((Integer) row[1], adminCategoryDTO);
        }
        return new ArrayList<AdminCategoryDTO>(categoryDTOMap.values());
    }

    @Override
    public List<AdminCategoryDTO> getFullDeletedCategories() {
        List<Object[]> fullDeleteCate = adminCategoryRepository.getFullDeleteCategories();
        List<AdminCategoryDTO> result = new ArrayList<>();

        for (Object[] row : fullDeleteCate) {
            int mainId = (Integer) row[0];
            String mainName = (String) row[1];
            int subId = (Integer) row[2];
            String subName = (String) row[3];
            int subSubId = (Integer) row[4];
            String subSubName = (String) row[5];
            int isDeleted = (Integer) row[6];

            AdminCategoryDTO adminCategoryDTO = new AdminCategoryDTO(
                    mainId, mainName, subId, subName, subSubId, subSubName, isDeleted
            );
            result.add(adminCategoryDTO);
        }
        return result;
    }

    @Override
    public List<AdminCategoryDTO> getFullSubDeletedCategories() {
        List<Object[]> fullSubDeletedCate = adminCategoryRepository.getFullDeleteSubCategories();
        List<AdminCategoryDTO> result = new ArrayList<>();

        for (Object[] row : fullSubDeletedCate) {
            int subId = (Integer) row[0];
            String subName = (String) row[1];
            int subSubId = (Integer) row[2];
            String subSubName = (String) row[3];
            int isDeleted = (Integer) row[4];

            AdminCategoryDTO adminCategoryDTO = new AdminCategoryDTO(
                    subId, subName, subSubId, subSubName, isDeleted
            );
            result.add(adminCategoryDTO);
        }
        return result;
    }


}
