package com.cakkie.backend.implement;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.model.products;
import com.cakkie.backend.repository.ProductRepository;
import com.cakkie.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImplement implements ProductService {
    @Autowired
    private ProductRepository productRepository;

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
    public List<Object[]> getProductDescriptionById(int id) {
        return productRepository.getProductDescriptionById(id);
    }

    @Override
    public List<productItem> getProductsByUserId(int userId) {
        return List.of();
    }

    @Override
    public List<productItem> getProductsByProductId(int productId) {
//        return productRepository.getProductItemById(productId);
        return  null;
    }

    @Override
    public List<productItem> getActiveProducts() {
        return List.of();
    }

    @Override
    public productItem saveProduct(productItem entity) {
        return null;
    }

    @Override
    public productItem updateProduct(productItem entity) {
        return null;
    }

    @Override
    public void deleteProduct(int id) {

    }
}
