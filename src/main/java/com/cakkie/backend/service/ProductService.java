package com.cakkie.backend.service;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductInfoDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.repository.CategoryRepository;
import com.cakkie.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String IMG_URL = "D:/CAKKE_PROJECT/cakkie_frontend/public/images/";

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    private String saveImg(MultipartFile file) throws IOException {
        File dir = new File(IMG_URL);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(IMG_URL + fileName);
        Files.write(path, file.getBytes());
        return fileName;
    }

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
            Long price = ((Number) row[10]).longValue();
            String productTitle = (String) row[11];
            String productInfo = (String) row[12];
            int discountId = ((Number) row[13]).intValue();
            String discountName = (String) row[14];
            int discountRate = ((Number) row[15]).intValue();
            String startDate = (String) row[16];
            String endDate = (String) row[17];

            ProductDTO productDTO = productsMap.getOrDefault(productId, new ProductDTO(
                    productId, productName, cateId, cateName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>(),
                    discountId, discountName, discountRate, startDate, endDate
            ));

            ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity, price);
            if (!productDTO.getProductItem().stream().anyMatch(item -> item.getId() == productItemId)) {
                productDTO.getProductItem().add(productItemDTO);
            }

            ProductInfoDTO productInfoDTO = new ProductInfoDTO(productTitle, productInfo);
            if (!productDTO.getProductInfo().stream().anyMatch(info -> info.getTitle().equals(productTitle))) {
                productDTO.getProductInfo().add(productInfoDTO);
            }

            productsMap.put(productId, productDTO);
        }

        return new ArrayList<>(productsMap.values());
    }

    public ProductDTO getProductById(int id) {
        List<Object[]> productData = productRepo.getProductsById(id);

        if (productData.isEmpty()) {
            throw new ProductNotFound("Product with ID " + id + " not found");
        }

        Object[] row = productData.get(0);
        ProductDTO product = new ProductDTO(
                (Integer) row[0], // productId (p.id)
                "",  // productName (p.description)
                0,                // cateId - Set appropriate value if available
                "",               // cateName (pdt.desTitleName) - Set if needed
                (String) row[1],  // description (pdi.desInfo)
                "",               // productImage - Set if available
                0,                // productRating - Set if available
                new ArrayList<>(), // productItems - not needed as per original design
                new ArrayList<>(), // productInfo - to be populated
                0,                // discountId - Set if applicable
                "",               // discountName - Set if available
                0,                // discountRate - Set if available
                "",               // startDate - Set if applicable
                ""                // endDate - Set if applicable
        );

        // Use a Set for unique titles
        Set<String> titlesSet = new HashSet<>();

        // Loop through the result rows to add ProductInfo entries
        for (Object[] rows : productData) {
            String productTitle = (String) rows[2]; // pdt.desTitleName
            String productInfo = (String) rows[3];  // pdi.desInfo

            // Check if the title has already been added
            if (titlesSet.add(productTitle)) {
                // Create and add ProductInfoDTO only if the title is unique
                ProductInfoDTO productInfoDTO = new ProductInfoDTO(productTitle, productInfo);
                product.getProductInfo().add(productInfoDTO);
            }
        }

        return product;
    }



}