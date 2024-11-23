package com.cakkie.backend.repository.adminProduct;

import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminAddProductSizeRepo extends JpaRepository<productItem, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE product_item SET is_deleted = 0 WHERE id = ?1 AND qty_in_stock = 0", nativeQuery = true)
    int updateIsDeletedIfQtyZero(Integer id);


    @Query(value = "SELECT pr.id, p.id, p.size, p.price, p.qty_in_stock\n" +
            "FROM product_item p\n" +
            "JOIN product pr ON p.pro_id = pr.id\n" +
            "WHERE pr.id = ?1 AND p.is_deleted = 1", nativeQuery = true)
    List<Object[]> getProductSizeById(Integer id);
}
