package com.cakkie.backend.service;

import com.cakkie.backend.dto.AddressDTO;
import com.cakkie.backend.dto.CouponDTO;
import com.cakkie.backend.dto.productCartDTO;
import com.cakkie.backend.model.coupons;
import com.cakkie.backend.model.productCart;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.shoppingCartItem;

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
      ProductDTO getProductItemById(int id);
      List<productItem> getProductsByUserId(int userId);
      List<productItem> getProductsByProductId(int productId);
      List<productItem> getActiveProducts();
      productItem saveProduct(productItem entity);
      productItem updateProduct(productItem entity);
      void deleteProduct(int id);
}
