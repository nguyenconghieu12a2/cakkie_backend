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

        // Debug: Print query result size
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
                    productRating, new ArrayList<>(), new ArrayList<>()
            ));

            ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity, price);
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
                new ArrayList<>()
        );

        Set<String> titlesSet = new HashSet<>();

        for (Object[] rows : productData) {
            int desTitleId = ((Number) rows[2]).intValue();
            String productInfo = (String) rows[4];
            if (titlesSet.add(productInfo)) {
                ProductInfoDTO productInfoDTO = new ProductInfoDTO(desTitleId, productInfo, 1);
                product.getProductInfo().add(productInfoDTO);
            }
        }

        return product;
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
        newProduct.setIsDeleted(isDelete);

        product savedProduct = productRepo.save(newProduct);

        // Add product item
        productItem newProductItem = new productItem();
        newProductItem.setProId(savedProduct);
        newProductItem.setSize(size);
        newProductItem.setQtyInStock(qtyInStock);
        newProductItem.setPrice(price);
        productItemRepo.save(newProductItem);

        return savedProduct;
    }


    public productDesInfo addDescriptionToProduct(int productId, int desTitleID, String desInfo, int isDeleted) {
        // Debug: Log productId and desTitleID
        System.out.println("Adding description to product. Product ID: " + productId + ", Title ID: " + desTitleID);

        // Find the product by its ID
        product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));

        // Debug: Log if product is found
        System.out.println("Product found: " + product);

        // Find the description title by its ID
        productDesTitle desTitle = productDesTitleRepo.findById(desTitleID)
                .orElseThrow(() -> new IllegalArgumentException("Title with ID " + desTitleID + " not found"));

        // Debug: Log if title is found
        System.out.println("Title found: " + desTitle);

        // Create a new productDesInfo and set its properties
        productDesInfo newDescriptionInfo = new productDesInfo();
        newDescriptionInfo.setProID(product);  // Associate with product
        newDescriptionInfo.setDesTitleId(desTitle);  // Associate with title
        newDescriptionInfo.setDesInfo(desInfo);  // Set the description info
        newDescriptionInfo.setIsDeleted(isDeleted);  // Set delete status

        // Save and return the new description info
        return productDesInfoRepo.save(newDescriptionInfo);
    }


    //Update Product
    public product updateProduct(
            int productId,
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

        Optional<productItem> existingProductItemOpt = productItemRepo.findByProIdAndSize(existingProduct, size);
        if (existingProductItemOpt.isPresent()) {
            productItem existingProductItem = existingProductItemOpt.get();
            existingProductItem.setQtyInStock(qtyInStock);
            existingProductItem.setPrice(price);
            productItemRepo.save(existingProductItem);
        } else {
            productItem newProductItem = new productItem();
            newProductItem.setProId(existingProduct);
            newProductItem.setSize(size);
            newProductItem.setQtyInStock(qtyInStock);
            newProductItem.setPrice(price);
            productItemRepo.save(newProductItem);
        }

        return productRepo.save(existingProduct);
    }


    public List<String> getAllSize() {
        return productRepository.getProductsSize();
    }

    public List<ProductDTO> getAllDeletedProduct() {
        List<Object[]> results = productRepo.getAllDeletedProducts();
        Map<Integer, ProductDTO> productsMap = new HashMap<>();

        for (Object[] row : results) {
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

            ProductDTO productDTO = productsMap.computeIfAbsent(productId, k -> new ProductDTO(
                    productId, productName, categoryId, categoryName, description, productImage,
                    productRating, new ArrayList<>(), new ArrayList<>()
            ));

            ProductItemDTO productItemDTO = new ProductItemDTO(productItemId, productSize, quantity, price);
            if (productDTO.getProductItem().stream().noneMatch(item -> item.getId() == productItemId)) {
                productDTO.getProductItem().add(productItemDTO);
            }

            int desTitleId = ((Number) row[11]).intValue();
            String desTitle = (String) row[12];
            String info = (String) row[13];
            int isDelete = ((Number) row[14]).intValue();
            if (productDTO.getProductInfo().stream().noneMatch(infoDTO -> infoDTO.getDesTitleID() == desTitleId)) {
                productDTO.getProductInfo().add(new ProductInfoDTO(desTitleId,desTitle, info, isDelete));
            }
        }

        return new ArrayList<>(productsMap.values());
    }


    public product deleteProduct(int productId) {
        product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " not found"));
        existingProduct.setIsDeleted(0);
        return productRepo.save(existingProduct);
    }

}