package com.cakkie.backend.service;

import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.AdminAddProductSizeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAddProductSizeService {

    private final AdminAddProductSizeRepo adminAddProductSizeRepo;

    public productItem addProductSize(productItem productItem) {
        return adminAddProductSizeRepo.save(productItem);
    }
}
