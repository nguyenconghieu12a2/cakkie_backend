package com.cakkie.backend.service;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductInfoDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.dto.ProductTitleDTO;
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
import java.text.NumberFormat;
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
        System.out.println("Total result rows: " + results.size());

        for (Object[] row : results) {
            System.out.println("Processing row: " + Arrays.toString(row)); // Debug

            if (row.length < 11) {
                System.out.println("Skipping row with insufficient columns: " + Arrays.toString(row)); // Debug
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

            ProductDTO productDTO = productsMap.getOrDefault(productId, new ProductDTO(
                    productId, productName, categoryId, categoryName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            ));

            ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity, price, 1);
            if (!productDTO.getProductItem().stream().anyMatch(item -> item.getId() == productItemId)) {
                productDTO.getProductItem().add(productItemDTO);
            }

            productsMap.put(productId, productDTO);
        }

        // Debug: Print the final products map size
        System.out.println("Total products in map: " + productsMap.size());

        return new ArrayList<>(productsMap.values());
    }


    public ProductDTO getProductById(int id) {
        List<Object[]> productData = productRepo.getProductsById(id);

        if (productData.isEmpty()) {
            throw new ProductNotFound("Product with ID " + id + " not found");
        }

        Object[] firstRow = productData.get(0);
        ProductDTO productDTO = new ProductDTO(
                (Integer) firstRow[0],  // Product ID
                "",                     // Product Name (Assuming it needs to be retrieved elsewhere)
                0,                      // Category ID (Assuming it needs to be retrieved elsewhere)
                "",                     // Category Name (Assuming it needs to be retrieved elsewhere)
                (String) firstRow[1],   // Product Description
                "",                     // Product Image (Assuming it needs to be retrieved elsewhere)
                0,                      // Product Rating (Assuming it needs to be retrieved elsewhere)
                new ArrayList<>(),      // Product Items list
                new ArrayList<>(),      // Product Titles list
                new ArrayList<>()       // Product Info list
        );

        Set<Integer> titleIdsSet = new HashSet<>();
        Set<Integer> infoIdsSet = new HashSet<>();

        for (Object[] row : productData) {
            Integer desTitleID = (Integer) row[2];
            String desTitleName = (String) row[3];
            String desInfo = (String) row[4];

            if (desTitleID != null && titleIdsSet.add(desTitleID)) {
                ProductTitleDTO titleDTO = new ProductTitleDTO(desTitleID, desTitleName, 1); // Assuming isDelete = 1
                productDTO.getProductTitle().add(titleDTO);
            }

            if (desTitleID != null && desInfo != null && infoIdsSet.add(desTitleID)) {
                ProductInfoDTO infoDTO = new ProductInfoDTO(desTitleID, desInfo, 1); // Assuming isDelete = 1
                productDTO.getProductInfo().add(infoDTO);
            }
        }

        return productDTO;
    }

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
        // Validate and retrieve category
        category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

        // Save product image
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
        newProduct.setIsDeleted(1);

        product savedProduct = productRepo.save(newProduct);

        // Add product item
        productItem newProductItem = new productItem();
        newProductItem.setProId(savedProduct);
        newProductItem.setSize(size);
        newProductItem.setQtyInStock(qtyInStock);
        newProductItem.setPrice(price);
        newProductItem.setIsDeleted(1);
        productItemRepo.save(newProductItem);

        return savedProduct;
    }

    public product updateProduct(
            int productId,
            int categoryId,
            String name,
            String description,
            MultipartFile productImage,
            int productRating,
            int isDelete,
            List<Map<String, Object>> sizes
    ) throws IOException {
        product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
        existingProduct.setCategoryID(category);

        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setProductRating(productRating);
        existingProduct.setIsDeleted(isDelete);

        if (productImage != null && !productImage.isEmpty()) {
            String imgPath = saveImage(productImage);
            existingProduct.setProductImage(imgPath);
        }

        // Update existing sizes, quantities, and prices
        for (Map<String, Object> sizeInfo : sizes) {
            String oldSize = sizeInfo.get("oldSize").toString(); // This should represent the size name to be updated
            String newSize = sizeInfo.get("size").toString(); // This is the new size name
            long qtyInStock = Long.parseLong(sizeInfo.get("qtyInStock").toString());
            long price = Long.parseLong(sizeInfo.get("price").toString());

            Optional<productItem> existingProductItemOpt = productItemRepo.findByProIdAndSize(existingProduct, oldSize);
            if (existingProductItemOpt.isPresent()) {
                // Update existing product item
                productItem existingProductItem = existingProductItemOpt.get();
                existingProductItem.setSize(newSize); // Update the size name
                existingProductItem.setQtyInStock(qtyInStock);
                existingProductItem.setPrice(price);
                productItemRepo.save(existingProductItem);
            } else {
                throw new IllegalArgumentException("Product item with size " + oldSize + " does not exist.");
            }
        }

        return productRepo.save(existingProduct);
    }




    public List<String> getAllSize() {
        return productRepository.getProductsSize();
    }

    public List<ProductDTO> getAllDeletedProduct() {
        List<Object[]> results = productRepo.getAllDeletedProducts();
        Map<Integer, ProductDTO> productsMap = new HashMap<>();

        System.out.println("Number of rows returned: " + results.size());

        for (Object[] row : results) {
            if (row.length < 11) {
                System.out.println("Skipping row with insufficient columns: " + Arrays.toString(row)); // Debug
                continue;
            }

            Integer productId = (Integer) row[0];
            String productName = (String) row[1];
            Integer categoryId = (row[2] != null) ? ((Number) row[2]).intValue() : null;
            String categoryName = (String) row[3];
            String description = (String) row[4];
            String productImage = (String) row[5];
            Integer productRating = (row[6] != null) ? ((Number) row[6]).intValue() : null;
            Integer productItemId = (row[7] != null) ? ((Number) row[7]).intValue() : null;
            String productSize = (String) row[8];
            Integer quantity = (row[9] != null) ? ((Number) row[9]).intValue() : null;
            Long price = (row[10] != null) ? ((Number) row[10]).longValue() : null;

            ProductDTO productDTO = productsMap.computeIfAbsent(productId, k -> new ProductDTO(
                    productId, productName, categoryId != null ? categoryId : 0,
                    categoryName, description, productImage, productRating != null ? productRating : 0,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            ));

            if (productItemId != null) {
                ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity != null ? quantity : 0, price != null ? price : 0L, 0);
                if (productDTO.getProductItem().stream().noneMatch(item -> item.getId() == productItemId)) {
                    productDTO.getProductItem().add(productItemDTO);
                }
            }
        }

        System.out.println("Total products in map: " + productsMap.size());

        return new ArrayList<>(productsMap.values());
    }

    public List<ProductDTO> getCategryDeleteProduct() {
        List<Object[]> results = productRepo.getDeletedCategoryProducts();
        Map<Integer, ProductDTO> productsMap = new HashMap<>();

        System.out.println("Number of rows returned: " + results.size());

        for (Object[] row : results) {
            if (row.length < 11) {
                System.out.println("Skipping row with insufficient columns: " + Arrays.toString(row)); // Debug
                continue;
            }

            Integer productId = (Integer) row[0];
            String productName = (String) row[1];
            Integer categoryId = (row[2] != null) ? ((Number) row[2]).intValue() : null;
            String categoryName = (String) row[3];
            String description = (String) row[4];
            String productImage = (String) row[5];
            Integer productRating = (row[6] != null) ? ((Number) row[6]).intValue() : null;
            Integer productItemId = (row[7] != null) ? ((Number) row[7]).intValue() : null;
            String productSize = (String) row[8];
            Integer quantity = (row[9] != null) ? ((Number) row[9]).intValue() : null;
            Long price = (row[10] != null) ? ((Number) row[10]).longValue() : null;

            ProductDTO productDTO = productsMap.computeIfAbsent(productId, k -> new ProductDTO(
                    productId, productName, categoryId != null ? categoryId : 0,
                    categoryName, description, productImage, productRating != null ? productRating : 0,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            ));

            if (productItemId != null) {
                ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity != null ? quantity : 0, price != null ? price : 0L, 0);
                if (productDTO.getProductItem().stream().noneMatch(item -> item.getId() == productItemId)) {
                    productDTO.getProductItem().add(productItemDTO);
                }
            }
        }

        System.out.println("Total products in map: " + productsMap.size());

        return new ArrayList<>(productsMap.values());
    }


    public product deleteProduct(int productId) {
        product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        existingProduct.setIsDeleted(0);

        return productRepo.save(existingProduct);
    }

    public product recoverProduct (int productId) {
        product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        existingProduct.setIsDeleted(1);

        return productRepo.save(existingProduct);
    }

    public void addNewDesInfoUsingInsertQuery(int productId, int desTitleId, String desInfo, int isDeleted) {
        product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        productDesTitle desTitle = productDesTitleRepo.findById(desTitleId)
                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleId + " not found"));

        productDesInfoRepo.insertProductDesInfo(product.getId(), desTitle.getDesTitleID(), desInfo, isDeleted);
    }

    public List<String> getAllDesTitles() {
        return productDesTitleRepo.getAllDesTitle();
    }

    public void updateProductDesInfo(int productId, int desTitleId, String desInfo) {
        product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        productDesTitle desTitle = productDesTitleRepo.findById(desTitleId)
                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleId + " not found"));

        productDesInfoRepo.updateProductDesInfo(productId, desTitleId, desInfo);
    }

    public void deleteProductDesInfo(int productId, int desTitleId) {
        product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        productDesTitle desTitle = productDesTitleRepo.findById(desTitleId)
                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleId + " not found"));

        productDesInfoRepo.deleteDesInfo(productId, desTitleId);
    }
}