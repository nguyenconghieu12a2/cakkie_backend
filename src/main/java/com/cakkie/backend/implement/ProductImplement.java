package com.cakkie.backend.implement;

import com.cakkie.backend.dto.*;
import com.cakkie.backend.model.*;
import com.cakkie.backend.repository.ProductItemRepository;
import com.cakkie.backend.repository.ProductRepository;
import com.cakkie.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImplement implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Override
    public List<productItem> getAllProductItems() {
        return productRepository.getAllProductItems();
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.getAllProduct();
    }
    @Override
    public List<Object[]> getAllProducts() {
        return productRepository.getAllProducts();
    }
    @Override
    public List<Object[]> getProductById(int id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<DescriptionDTO> getProductDescriptionsById(int id) {
        return productRepository.getProductDescriptionsById(id);
    }

//    @Override
//    public List<ProductDTO> getProductsById(int id) {
//        return productRepository.getProductsById(id);
//    }

    public void restoreProductQuantity(productItem product, int quantity) {
        // Assuming that Product has a field for stock (quantity)
        long currentStock = product.getQtyInStock();
        product.setQtyInStock(currentStock + quantity);
        productItemRepository.save(product); // Save the updated product
    }
    @Override
    public List<Object[]> getProductDescriptionById(int id) {
        return productRepository.getProductDescriptionById(id);
    }

    @Override
    public CouponDTO getCouponById(int id) {
        return productRepository.getCouponById(id);
    }

    @Override
    public List<productCartDTO> getProductCart(int userId) {
        return productRepository.getProductCart(userId);
    }

    @Override
    public List<AddressDTO> getAddressById(int userId) {
        return productRepository.getAddressById(userId);
    }

    @Override
    public List<ProductDTO> getProductItemById(int productId) {
        return productRepository.getProductItemById(productId);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(int userId) {
        return productRepository.getOrdersByUserId(userId);
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(int orderId) {
        List<Object[]> results = productRepository.getOrderItemsByOrderId(orderId);
        List<OrderItemDTO> orderItemList = new ArrayList<OrderItemDTO>();
        for (Object[] result : results) {
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.setId((Integer) result[0]);
            orderItem.setOrderId((Integer) result[1]);
            orderItem.setProductItemId((Integer) result[2]);
            orderItem.setQuantity((Integer) result[3]);
            orderItem.setPrice((long) result[4]);
            orderItem.setDiscountPrice((long) result[5]);
            orderItem.setNote((String) result[6]);
            orderItem.setReviewId((Integer) result[7]);
            orderItem.setOrderLineId((Integer) result[8]);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    @Override
    public List<CouponDTO> getCoupons() {
        return productRepository.getCoupons();
    }

    @Override
    public List<ShippingMethodDTO> getShippingMethod() {
        return productRepository.getShippingMethod();
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethods() {
        return productRepository.getPaymentMethods();
    }

}