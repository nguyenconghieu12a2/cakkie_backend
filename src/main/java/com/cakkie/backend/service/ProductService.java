package com.cakkie.backend.service;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductInfoDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.model.category;
import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.CategoryRepository;
import com.cakkie.backend.repository.ProductItemRepository;
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

    private static final String IMG_URLL = "D:/CAKKE_PROJECT/cakkie_frontend/public/images/";

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductItemRepository productItemRepo;

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return ""; // Return an empty string if no image is provided
        }

        File directory = new File(IMG_URLL);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String originalFilename = imageFile.getOriginalFilename();
        String baseName = originalFilename != null ? originalFilename.replaceAll("\\.[^.]+$", "") : "image";
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        Path path = Paths.get(IMG_URLL + originalFilename);
        int counter = 1;

        // Check if the file with this name already exists, and modify the name if necessary
        while (Files.exists(path)) {
            String newFilename = baseName + "(" + counter + ")" + extension;
            path = Paths.get(IMG_URLL + newFilename);
            counter++;
        }

        Files.write(path, imageFile.getBytes());
        return path.getFileName().toString();
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
                (Integer) row[0],
                "",
                0,
                "",
                (String) row[1],
                "",
                0,
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                "",
                0,
                "",
                ""
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

    public product addProduct(int categoryId, String name, String description, MultipartFile productImage, int productRating, int isDelete, String size, long qtyInStock, long price) throws IOException {
        String imgPath = (productImage != null) ? saveImage(productImage) : null;

        // Create a new product and set properties
        product newProduct = new product();
        category cate = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));

        newProduct.setCategoryID(cate);
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setProductImage(imgPath);
        newProduct.setProductRating(productRating);
        newProduct.setIsDeleted(isDelete);

        product savedProduct = productRepo.save(newProduct);

        // Ensure productItemList is initialized
        if (savedProduct.getProductItemList() == null) {
            savedProduct.setProductItemList(new ArrayList<>());
        }

        // Create and save productItem
        productItem newProductItem = new productItem();
        newProductItem.setProId(savedProduct);
        newProductItem.setSize(size);
        newProductItem.setQtyInStock(qtyInStock);
        newProductItem.setPrice(price);
        newProductItem.setProductImage(imgPath);
        productItemRepo.save(newProductItem);

        // Add the item to the product's item list
        savedProduct.getProductItemList().add(newProductItem);
        productRepo.save(savedProduct);

        return savedProduct;
    }
}