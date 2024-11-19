package com.cakkie.backend.service.adminOthers;

import com.cakkie.backend.dto.product.ProductDTO;
import com.cakkie.backend.dto.product.ProductInfoDTO;
import com.cakkie.backend.dto.product.ProductItemDTO;
import com.cakkie.backend.repository.adminOthers.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<ProductDTO> getAllProducts() {
        List<Object[]> results = productRepo.getAllProducts();
        Map<Integer, ProductDTO> productsMap = new HashMap<>();

        for (Object[] row : results) {
            int productId = (Integer) row[0];
            String productName = (String) row[1];
            int cateId = ((Number) row[2]).intValue();
            String cateName = (String) row[3];
            String description = (String) row[4];
            String productImage = (String) row[5];
            int productRating = ((Number) row[6]).intValue();
            int productItemId = ((Number) row[7]).intValue();
            String productSize = (String) row[8];
            int quantity = ((Number) row[9]).intValue();
            Long price = ((Number) row[10]).longValue(); // maybe error will occur here
            String productTitle = (String) row[11];
            String productInfo = (String) row[12];
            int discountId = ((Number) row[13]).intValue();
            String discountName = (String) row[14];
            int discountRate = ((Number) row[15]).intValue();
            String startDate = (String) row[16];
            String endDate = (String) row[17];

            // Create or retrieve the ProductDTO from the map
            ProductDTO productDTO = productsMap.getOrDefault(productId, new ProductDTO(
                    productId, productName, cateId, cateName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>(),
                    discountId, discountName, discountRate, startDate, endDate
            ));

            // Create and add ProductItemDTO
            ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity, price);
            if(!productDTO.getProductItem().stream().anyMatch(item -> item.getId() == productItemId)) {
                productDTO.getProductItem().add(productItemDTO);
            }

            // Create and add ProductItemDTO
            ProductInfoDTO productInfoDTO = new ProductInfoDTO(productTitle, productInfo);
            if(!productDTO.getProductInfo().stream().anyMatch(info -> info.getTitle().equals(productTitle))) {
                productDTO.getProductInfo().add(productInfoDTO);
            }

            // Put the ProductDTO back into the map
            productsMap.put(productId, productDTO);
        }

        return new ArrayList<>(productsMap.values());
    }
}
