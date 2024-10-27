package com.cakkie.backend.controller;

import com.cakkie.backend.api.TodoAPI;
import com.cakkie.backend.dto.AddressDTO;
import com.cakkie.backend.dto.CouponDTO;
import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.productCartDTO;
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
    public ProductResponse getProducts(@PathVariable String id) {
        List<Object[]> results = productService.getProductById(Integer.parseInt(id));
        List<Object[]> resultsDes = productService.getProductDescriptionById(Integer.parseInt(id));
        // Use a Map to group products by product ID
        List<products> productList = new ArrayList<products>();

        // Process product results
        for (Object[] result : results) {
            int productId = (int) result[0];
            products product = new products();

                product = new products();
                product.setProductID(productId);
                product.setName((String) result[1]);
                product.setDescription((String) result[2]);
                product.setCategoryName((String) result[3]);
                product.setPrice((Long) result[4]);
                product.setProductImage((String) result[5]);
                product.setProductRating((int) result[6]);
                product.setSize((String) result[7]);
                product.setQuantityStock((long) result[8]);

                product.setDescriptions(new ArrayList<>());
                productList.add(product);
        }

        List<productsDescription> descriptionList = new ArrayList<>();

        // Process description results and add to the corresponding product
        for (Object[] desc : resultsDes) {
            int productId = (int) desc[0];

                productsDescription description = new productsDescription();
                description.setDesId((int) desc[1]);
                description.setDescription((String) desc[2]);
                description.setDescriptionTitle((String) desc[3]);

                // Also add it to the separate description list
                descriptionList.add(description);
        }


        // Create the response object
        ProductResponse response = new ProductResponse();
        response.setProductList(productList);
        response.setDescriptionList(descriptionList);

        // Return the response containing both the list of products and descriptions
        return response;
    }

    @GetMapping("coupon/{id}")
    public CouponDTO getCoupons(@PathVariable String id) {
        return productService.getCouponById(Integer.parseInt(id));
    }

    @GetMapping("cart/{userId}")
    public productCartDTO getCart(@PathVariable String userId) { return productService.getProductCart(Integer.parseInt(userId)); }

    @GetMapping("address/{userId}")
    public List<AddressDTO> getAddressById(@PathVariable String userId) { return productService.getAddressById(Integer.parseInt(userId)); }

    @GetMapping("productItem/{id}")
    public ProductDTO getProductItemById(@PathVariable String id) {return productService.getProductItemById(Integer.parseInt(id));}

}
