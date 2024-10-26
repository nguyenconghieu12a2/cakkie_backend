package com.cakkie.backend.service;

import com.cakkie.backend.model.coupons;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {

      List<ProductDTO> getAllProduct();
      List<Object[]> getAllProducts();
      List<productItem> getAllProductItems();
      List<Object[]> getProductById(int id);
      List<Object[]> getProductDescriptionById(int id);
      coupons getCouponById(int id);
      List<productItem> getProductsByUserId(int userId);
      List<productItem> getProductsByProductId(int productId);
      List<productItem> getActiveProducts();
      productItem saveProduct(productItem entity);
      productItem updateProduct(productItem entity);
      void deleteProduct(int id);
}
