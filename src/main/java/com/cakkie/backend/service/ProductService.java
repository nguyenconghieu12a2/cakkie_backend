package com.cakkie.backend.service;

import com.cakkie.backend.dto.*;
import com.cakkie.backend.model.productItem;

import java.util.List;

public interface ProductService {

      List<ProductDTO> getAllProduct();
      List<Object[]> getAllProducts();
      List<productItem> getAllProductItems();
      List<Object[]> getProductById(int id);
      List<DescriptionDTO> getProductDescriptionsById(int id);
//      List<ProductDTO> getProductsById(int id);
      List<Object[]> getProductDescriptionById(int id);
      CouponDTO getCouponById(int id);
      List<productCartDTO> getProductCart(int userId);
      List<AddressDTO> getAddressById(int id);
      List<ProductDTO> getProductItemById(int productId);
      List<OrderDTO> getOrdersByUserId(int userId);
      List<OrderItemDTO> getOrderItemsByOrderId(int orderId);
}
