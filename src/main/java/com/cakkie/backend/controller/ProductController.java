package com.cakkie.backend.controller;

import com.cakkie.backend.api.TodoAPI;
import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.*;
import com.cakkie.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/Product")
    public TodoAPI homeController(){
        TodoAPI todoApi = new TodoAPI();
        todoApi.setMessage("welcome to Product");
        todoApi.setStatus(true);
        return todoApi;
    }

    @GetMapping("/Product/getAll")
    public  List<ProductDTO> getAllProduct(){
        return productService.getAllProduct();
    }
    @GetMapping("/Product/All")
    public List<productItem> getAllProductItems() {
        return productService.getAllProductItems();
    }
    @GetMapping("/Product/Products")
    public List<products> getAllProducts() {
        List<Object[]> results = productService.getAllProducts();
        List<products> products = new ArrayList<>();
        for (Object[] result : results) {
            products product = new products();
            product.setProductID((int) result[0]);
            product.setName((String) result[1]);
            product.setDescription((String) result[2]);
            product.setCategoryName((String) result[3]);
            product.setPrice((Long) result[4]);
            product.setProductImage((String) result[5]);
//            product.setProductRating((int) result[6]);
            product.setSize((String) result[7]);
            product.setQuantityStock((long) result[8]);
            product.setDesInfo((String) result[9]);
            product.setDesTitleName((String) result[10]);
            products.add(product);
        }
        return products;
    }

    @GetMapping("/Product/{id}")
    public List<products> getProducts(@PathVariable String id){
        List<Object[]> results = productService.getProductById(Integer.parseInt(id));
        List<Object[]> resultsDes = productService.getProductDescriptionById(Integer.parseInt(id));

        // Use a Map to group products by product ID
        Map<Integer, products> productMap = new HashMap<>();

        // Process product results
        for (Object[] result : results) {
            int productId = (int) result[0]; // Assuming the product ID is in the first column
            products product = productMap.get(productId);

            if (product == null) {
                // Create a new product if it doesn't exist in the map
                product = new products();
                product.setProductID(productId);
                product.setName((String) result[1]);
                product.setDescription((String) result[2]);
                product.setCategoryName((String) result[3]);
                product.setPrice((Long) result[4]);
                product.setProductImage((String) result[5]);
                // product.setProductRating((int) result[6]); // Uncomment if needed
                product.setSize((String) result[7]);
                product.setQuantityStock((long) result[8]);

                // Initialize the description list
                product.setDescriptions(new ArrayList<>()); // Assuming you add a setDescriptions method
                productMap.put(productId, product);
            }
        }

        // Process description results and add to the corresponding product
        for (Object[] desc : resultsDes) {
            int productId = (int) desc[0]; // Assuming the product ID is in the first column of the description results
            products product = productMap.get(productId);

            if (product != null) {
                productsDescription description = new productsDescription();
                description.setDescription((String) desc[1]); // Assuming description is in the second column
                description.setDescriptionTitle((String) desc[2]); // Assuming title is in the third column

                // Add the description to the product
                product.getDescriptions().add(description);
            }
        }

        // Convert the map values to a list
        return new ArrayList<>(productMap.values());
    }
}
