package com.cakkie.backend.service;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductInfoDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.exception.CategoryNotFound;
import com.cakkie.backend.exception.ProductNotFound;
import com.cakkie.backend.model.*;
import com.cakkie.backend.repository.*;
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
    private final ProductRepository productRepository;
    private final ProductDesTitleRepository productDesTitleRepo;
    private final ProductDesInfoRepository productDesInfoRepo;

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return "";
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
            if (row.length < 18) {
                continue; // Skip this row if it does not contain all required fields
            }

            int productId = (Integer) row[0];
            String productName = (String) row[1];
            int categoryId = ((Number) row[2]).intValue();
            String categoryName = (String) row[3];
            String description = (String) row[4];
            String productImage = (String) row[5];
            int productRating = ((Number) row[6]).intValue();
            int productItemId = ((Number) row[7]).intValue();
            String productSize = (String) row[8];
            int quantity = ((Number) row[9]).intValue();
            long price = ((Number) row[10]).longValue();
            String productTitle = (String) row[11];
            String productInfo = (String) row[12];
            int discountId = ((Number) row[13]).intValue();
            String discountName = (String) row[14];
            int discountRate = ((Number) row[15]).intValue();
            String startDate = (String) row[16];
            String endDate = (String) row[17];

            ProductDTO productDTO = productsMap.getOrDefault(productId, new ProductDTO(
                    productId, productName, categoryId, categoryName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>(),
                    discountId, discountName, discountRate, startDate, endDate, 1
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
                "",
                0
        );

        Set<String> titlesSet = new HashSet<>();

        for (Object[] rows : productData) {
            String productTitle = (String) rows[2];
            String productInfo = (String) rows[3];
            if (titlesSet.add(productTitle)) {
                ProductInfoDTO productInfoDTO = new ProductInfoDTO(productTitle, productInfo);
                product.getProductInfo().add(productInfoDTO);
            }
        }

        return product;
    }

    //Add Product
//    public product addProduct(int subSubCategoryId, String name, String description, MultipartFile productImage, int productRating, int isDelete, String size, long qtyInStock, long price) throws IOException {
//        String imgPath = (productImage != null) ? saveImage(productImage) : null;
//
//        category subSubCategory = categoryRepo.findById(subSubCategoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-sub-category"));
//
//        product newProduct = new product();
//        newProduct.setCategoryID(subSubCategory);
//        newProduct.setName(name);
//        newProduct.setDescription(description);
//        newProduct.setProductImage(imgPath);
//        newProduct.setProductRating(productRating);
//        newProduct.setIsDeleted(isDelete);
//
//        product savedProduct = productRepo.save(newProduct);
//
//        if (savedProduct.getProductItemList() == null) {
//            savedProduct.setProductItemList(new ArrayList<>());
//        }
//
//        Optional<productItem> existingProductItemOpt = productItemRepo.findByProIdAndSize(savedProduct, size);
//        if (existingProductItemOpt.isPresent()) {
//            productItem existingProductItem = existingProductItemOpt.get();
//            existingProductItem.setQtyInStock(existingProductItem.getQtyInStock() + qtyInStock);
//            existingProductItem.setPrice(price);
//            existingProductItem.setProductImage(imgPath);
//            productItemRepo.save(existingProductItem);
//        } else {
//            productItem newProductItem = new productItem();
//            newProductItem.setProId(savedProduct);
//            newProductItem.setSize(size);
//            newProductItem.setQtyInStock(qtyInStock);
//            newProductItem.setPrice(price);
//            newProductItem.setProductImage(imgPath);
//            productItemRepo.save(newProductItem);
//
//            savedProduct.getProductItemList().add(newProductItem);
//        }
//        productRepo.save(savedProduct);
//
//        return savedProduct;
//    }
//
//    public product addProduct(int subSubCategoryId, String name, String description, MultipartFile productImage, int productRating, int isDelete, String size, long qtyInStock, long price) throws IOException {
//        String imgPath = (productImage != null) ? saveImage(productImage) : null;
//
//        category subSubCategory = categoryRepo.findById(subSubCategoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-sub-category"));
//
//        product newProduct = new product();
//        newProduct.setCategoryID(subSubCategory);
//        newProduct.setName(name);
//        newProduct.setDescription(description);
//        newProduct.setProductImage(imgPath);
//        newProduct.setProductRating(productRating);
//        newProduct.setIsDeleted(isDelete);
//
//        product savedProduct = productRepo.save(newProduct);
//
//        if (savedProduct.getProductItemList() == null) {
//            savedProduct.setProductItemList(new ArrayList<>());
//        }
//
//        Optional<productItem> existingProductItemOpt = productItemRepo.findByProIdAndSize(savedProduct, size);
//        if (existingProductItemOpt.isPresent()) {
//            productItem existingProductItem = existingProductItemOpt.get();
//            existingProductItem.setQtyInStock(existingProductItem.getQtyInStock() + qtyInStock);
//            existingProductItem.setPrice(price);
//            existingProductItem.setProductImage(imgPath);
//            productItemRepo.save(existingProductItem);
//        } else {
//            productItem newProductItem = new productItem();
//            newProductItem.setProId(savedProduct);
//            newProductItem.setSize(size);
//            newProductItem.setQtyInStock(qtyInStock);
//            newProductItem.setPrice(price);
//            newProductItem.setProductImage(imgPath);
//            productItemRepo.save(newProductItem);
//
//            savedProduct.getProductItemList().add(newProductItem);
//        }
//        productRepo.save(savedProduct);
//
//        return savedProduct;
//    }
//
//    public productDesInfo addDescriptionToProduct(int productId, int desTitleId, String descriptionInfo, int isDeleted) {
//        product product = productRepo.findById(productId)
//                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));
//
//        productDesTitle desTitle = productDesTitleRepo.findById(desTitleId)
//                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleId + " not found"));
//
//        productDesInfo newDescriptionInfo = new productDesInfo();
//        newDescriptionInfo.setProID(product);
//        newDescriptionInfo.setDesTitleId(desTitle);
//        newDescriptionInfo.setDesInfo(descriptionInfo);
//        newDescriptionInfo.setIsDeleted(isDeleted);
//
//        return productDesInfoRepo.save(newDescriptionInfo);
//    }


    public product addProduct(
            int categoryId,
            String name,
            String description,
            MultipartFile productImage,
            int productRating,
            int isDelete,
            String size,
            long qtyInStock,
            long price
    ) throws IOException {
        // Validate the category
        category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

        // Save image and validate it
        String imgPath = (productImage != null && !productImage.isEmpty()) ? saveImage(productImage) : null;
        if (imgPath == null) {
            throw new IllegalArgumentException("Product image is required.");
        }

        // Create and save the new product
        product newProduct = new product();
        newProduct.setCategoryID(category);
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setProductImage(imgPath);
        newProduct.setProductRating(productRating);
        newProduct.setIsDeleted(isDelete);

        product savedProduct = productRepo.save(newProduct);

        // Add or update product item
        Optional<productItem> existingProductItemOpt = productItemRepo.findByProIdAndSize(savedProduct, size);
        if (existingProductItemOpt.isPresent()) {
            productItem existingProductItem = existingProductItemOpt.get();
            existingProductItem.setQtyInStock(existingProductItem.getQtyInStock() + qtyInStock);
            existingProductItem.setPrice(price);
            productItemRepo.save(existingProductItem);
        } else {
            productItem newProductItem = new productItem();
            newProductItem.setProId(savedProduct);
            newProductItem.setSize(size);
            newProductItem.setQtyInStock(qtyInStock);
            newProductItem.setPrice(price);
            productItemRepo.save(newProductItem);
        }
        return savedProduct;
    }

    public productDesInfo addDescriptionToProduct(int productId, int desTitleID, String desInfo, int isDeleted) {
        // Fetch and validate the product
        product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        // Fetch and validate the description title
        productDesTitle desTitle = productDesTitleRepo.findById(desTitleID)
                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleID + " not found"));

        // Create and save the new description information
        productDesInfo newDescriptionInfo = new productDesInfo();
        newDescriptionInfo.setProID(product);
        newDescriptionInfo.setDesTitleId(desTitle);
        newDescriptionInfo.setDesInfo(desInfo);
        newDescriptionInfo.setIsDeleted(isDeleted);

        return productDesInfoRepo.save(newDescriptionInfo);
    }




    public List<String> getAllSize() {
        return productRepository.getProductsSize();
    }

    public List<ProductDTO> getAllDeletedProduct() {
        List<Object[]> results = productRepo.getAllDeletedProducts();
        Map<Integer, ProductDTO> productsMap = new HashMap<>();

        for (Object[] row : results) {
            if (row.length < 18) {
                continue;
            }

            int productId = (Integer) row[0];
            String productName = (String) row[1];
            int categoryId = ((Number) row[2]).intValue();
            String categoryName = (String) row[3];
            String description = (String) row[4];
            String productImage = (String) row[5];
            int productRating = ((Number) row[6]).intValue();
            int productItemId = ((Number) row[7]).intValue();
            String productSize = (String) row[8];
            int quantity = ((Number) row[9]).intValue();
            long price = ((Number) row[10]).longValue();
            String productTitle = (String) row[11];
            String productInfo = (String) row[12];
            int discountId = ((Number) row[13]).intValue();
            String discountName = (String) row[14];
            int discountRate = ((Number) row[15]).intValue();
            String startDate = (String) row[16];
            String endDate = (String) row[17];

            ProductDTO productDTO = productsMap.getOrDefault(productId, new ProductDTO(
                    productId, productName, categoryId, categoryName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>(),
                    discountId, discountName, discountRate, startDate, endDate, 0
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

}