package com.cakkie.backend.repository;

import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<product, Integer>{

//    @Query(value = "SELECT new com.cakkie.backend.dto.ProductDTO("
//            + "p.id, p.category.id, p.name, p.description, p.productImage, COALESCE(p.productRating, 0), "
//            + "pi.size, pi.qtyInStock, pi.productImage, pi.price) "
//            + "FROM productItem pi "
//            + "JOIN pi.product p "
//            + "WHERE p.isDeleted = false AND pi.isDeleted = false")
//    List<ProductDTO> getAllProduct();


}
