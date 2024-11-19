package com.cakkie.backend.service.adminProduct;

import com.cakkie.backend.dto.adminProduct.AdminProductItemSizeDTO;
import com.cakkie.backend.exception.adminProduct.AdminProductNotFound;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.adminProduct.AdminAddProductSizeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAddProductSizeService {

    private final AdminAddProductSizeRepo adminAddProductSizeRepo;

    public productItem addProductSize(productItem productItem) {
        return adminAddProductSizeRepo.save(productItem);
    }

    public List<AdminProductItemSizeDTO> getProIdProItemIdSizeByProId(int proId) {
        List<Object[]> results = adminAddProductSizeRepo.getProductSizeById(proId);

        if (results.isEmpty()) {
            throw new AdminProductNotFound("No product items found for product ID: " + proId);
        }

        // Map raw results to DTOs
        return results.stream()
                .map(result -> new AdminProductItemSizeDTO(
                        (Integer) result[0], // proId
                        (Integer) result[1], // proItemId
                        (String) result[2], // size
                        (Long) result[3],
                        (Long) result[4]
                ))
                .toList();
    }

    public void deleteProductSize(int productItemId) {
        int updatedRows = adminAddProductSizeRepo.updateIsDeletedIfQtyZero(productItemId);
        if (updatedRows == 0) {
            throw new IllegalStateException("Cannot delete size because quantity in stock is not zero.");
        }
    }
}
