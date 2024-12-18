package com.cakkie.backend.controller;

import com.cakkie.backend.dto.*;
import com.cakkie.backend.model.*;
import com.cakkie.backend.service.ProductService;
import com.cakkie.backend.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController{
    @Autowired
    private ProductService productService;
    @Autowired
    private UserReviewService userReviewService;

    @GetMapping("/Product/getAll")
    public  List<ProductDTO> getAllProduct(){
        List<ProductDTO> products = productService.getAllProduct();

        // Add average rating for each product
        for (ProductDTO product : products) {
            double averageRating = userReviewService.getAverageRatingForProduct(product.getProductID());
            product.setAverageRating(averageRating);
        }

        return products;
    }
//    @GetMapping("/Product/All")
//    public List<productItem> getAllProductItems() {
//        return productService.getAllProductItems();
//    }
//
//    @GetMapping("/Product/Products")
//    public List<products> getAllProducts() {
//        List<Object[]> results = productService.getAllProducts();
//        List<products> products = new ArrayList<>();
//        for (Object[] result : results) {
//            products product = new products();
//            product.setProductID((int) result[0]);
//            product.setName((String) result[1]);
//            product.setDescription((String) result[2]);
//            product.setCategoryName((String) result[3]);
//            product.setPrice((Long) result[4]);
//            product.setProductImage((String) result[5]);
////            product.setProductRating((int) result[6]);
//            product.setSize((String) result[7]);
//            product.setQuantityStock((long) result[8]);
//            product.setDesInfo((String) result[9]);
//            product.setDesTitleName((String) result[10]);
//            products.add(product);
//        }
//        return products;
//    }

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
                product.setDiscount((double) result[9]);
                product.setProductItemId((int) result[10]);
                product.setIsDeleted((int) result[11]);
                product.setDescriptions(new ArrayList<>());
                double averageRating = userReviewService.getAverageRatingForProduct(product.getProductID());
                product.setAverageRating(averageRating);
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

    @GetMapping("/Product/{id}/reviews")
    public List<UserReviewDTO> getReviewsForProduct(@PathVariable int id) {
        return userReviewService.getReviewsForProduct(id);
    }

    @GetMapping("coupon/{id}")
    public CouponDTO getCoupons(@PathVariable String id) {
        return productService.getCouponById(Integer.parseInt(id));
    }

    @GetMapping("coupon/")
    public List<CouponDTO> getCouponsAll() {
        return productService.getCoupons();
    }

    @GetMapping("shippingMethod/")
    public List<ShippingMethodDTO> getShippingMethodAll() {
        return productService.getShippingMethod();
    }

    @GetMapping("getPaymentMethods/")
    public List<PaymentMethodDTO> getPaymentMethods() {
        return productService.getPaymentMethods();
    }

    @GetMapping("cart/{userId}")
    public List<productCartDTO> getCart(@PathVariable String userId) { return productService.getProductCart(Integer.parseInt(userId)); }

    @GetMapping("address/{userId}")
    public List<AddressDTO> GetAddressByUserId(@PathVariable String userId) { return productService.getAddressById(Integer.parseInt(userId)); }

    @GetMapping("productItem/{productId}")
    public List<ProductDTO> getProductItemById(@PathVariable String productId) {return productService.getProductItemById(Integer.parseInt(productId));}

    @GetMapping("order/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable String userId) {return productService.getOrdersByUserId(Integer.parseInt(userId));}

    @GetMapping("orderItem/{orderId}")
    public List<OrderItemDTO> getOrderItemsByOrderId(@PathVariable String orderId) {return productService.getOrderItemsByOrderId(Integer.parseInt(orderId));}

}
