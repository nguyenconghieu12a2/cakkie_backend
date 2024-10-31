package com.cakkie.backend.repository;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<product, Integer> {

    @Query("SELECT new com.cakkie.backend.dto.ProductDTO(" +
            "p.id, c.cateName, p.name, p.productImage, i.qtyInStock, i.price, i.size, i.productImage, p.isDeleted) " +
            "FROM product p JOIN p.productItemList i " +
            "JOIN p.categoryID c WHERE p.isDeleted = 1")
    List<ProductDTO> getAllProduct();
}
