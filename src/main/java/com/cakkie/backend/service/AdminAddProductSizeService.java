package com.cakkie.backend.service;

import com.cakkie.backend.dto.AdminAddProductSizeDTO;
import com.cakkie.backend.dto.ProductItemSizeDTO;
import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.AdminAddProductSizeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAddProductSizeService {

    private final AdminAddProductSizeRepo adminAddProductSizeRepo;

    public productItem addProductSize(productItem productItem) {
        return adminAddProductSizeRepo.save(productItem);
    }

    public List<ProductItemSizeDTO> getProIdProItemIdSizeByProId(int proId) {
        List<Object[]> results = adminAddProductSizeRepo.getProductSizeById(proId);

        if (results.isEmpty()) {
            throw new ProductNotFound("No product items found for product ID: " + proId);
        }

        // Map raw results to DTOs
        return results.stream()
                .map(result -> new ProductItemSizeDTO(
                        (Integer) result[0], // proId
                        (Integer) result[1], // proItemId
                        (String) result[2], // size
                        (Long) result[3],
                        (Long) result[4]
                ))
                .toList();
    }

    public void deleteProductSize(int productItemId) {
        adminAddProductSizeRepo.updateIsDeleted(productItemId);
    }
}
