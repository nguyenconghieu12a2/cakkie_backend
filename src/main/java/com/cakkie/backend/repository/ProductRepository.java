package com.cakkie.backend.repository;

import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.model.products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository  extends JpaRepository<productItem, Long>{
//    @Query(value = "SELECT  p.name, p.description, c.cateName, p.price, p.product_image, p.product_rating, pi.size, pi.qty_in_stock, pdi.desInfo, pdt.desTitle " +
//            "FROM product p " +
//            "JOIN category c ON p.categoryID = c.id " +
//            "JOIN product_item pi ON p.id = pi.pro_id " +
//            "JOIN productDesInfo pdi ON p.id = pdi.proID " +
//            "JOIN productDesTitle pdt ON pdi.desTitleID = pdt.desTitleID", nativeQuery = true)
    @Query(value = "SELECT * FROM product_item", nativeQuery = true)
    List<productItem> getAllProductItems();

    @Query(value = "SELECT  p.id, p.name, p.description, c.cate_name, pi.price, p.product_image, p.product_rating, pi.size, pi.qty_in_stock " +
            "            FROM product p " +
            "            JOIN category c ON p.categoryID = c.id " +
            "            JOIN product_item pi ON p.id = pi.pro_id ", nativeQuery = true)
    List<Object[]> getAllProducts();

    @Query(value = "SELECT  p.id, p.name, p.description, c.cate_name, pi.price, p.product_image, p.product_rating, pi.size, pi.qty_in_stock " +
            "            FROM product p " +
            "            JOIN category c ON p.categoryID = c.id " +
            "            JOIN product_item pi ON p.id = pi.pro_id " +
            "            WHERE p.id = :id", nativeQuery = true)
    List<Object[]> getProductById(@Param("id") int id);

    @Query(value = "SELECT p.id, pdi.desInfo, pdt.desTitleName " +
            "FROM product p" +
            "JOIN productDesInfo pdi ON p.id = pdi.proID " +
            "JOIN productDesTitle pdt ON pdi.desTitleID = pdt.desTitleID ", nativeQuery = true)
    List<Object[]> getProductDescriptionById(@Param("id") int id);
    // Query to get a product item by size
    @Query(value = "SELECT p FROM productItem p WHERE p.size = :size")
    List<productItem> getProductItemsBySize(@Param("size") String size);

    // Query to get a product item by ID
    @Query(value = "SELECT p FROM productItem p WHERE p.id = :id")
    productItem getProductItemById(@Param("id") int id);

    // Query to get product items that are not deleted
    @Query(value = "SELECT p FROM productItem p WHERE p.isDeleted = 0")
    List<productItem> getAvailableProductItems();

    // Query to get product items with a specific price range
    @Query(value = "SELECT p FROM productItem p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<productItem> getProductItemsByPriceRange(@Param("minPrice") long minPrice, @Param("maxPrice") long maxPrice);
}
