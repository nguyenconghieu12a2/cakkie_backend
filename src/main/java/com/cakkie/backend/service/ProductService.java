package com.cakkie.backend.service;

import com.cakkie.backend.dto.*;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.model.productCart;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.model.shoppingCartItem;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ProductService {

      List<ProductDTO> getAllProduct();
      List<Object[]> getAllProducts();
      List<productItem> getAllProductItems();
      List<Object[]> getProductById(int id);
      List<Object[]> getProductDescriptionById(int id);
      CouponDTO getCouponById(int id);
      productCartDTO getProductCart(int userId);
      List<AddressDTO> getAddressById(int id);
      List<ProductDTO> getProductItemById(int productId);
      List<OrderDTO> getOrdersByUserId(int userId);

      @Modifying
      @Transactional
      ProductItemDTO updateProductItem(int productId, ProductItemDTO productItemDTO);


      List<productItem> getProductsByUserId(int userId);
      List<productItem> getProductsByProductId(int productId);
      List<productItem> getActiveProducts();
      productCart saveProduct(productCart entity);
      productItem updateProduct(productItem entity);
      void deleteProduct(int id);
}
