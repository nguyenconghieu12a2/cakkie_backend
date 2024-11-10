package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BestSellerProductRepo extends JpaRepository<orderLine, Integer> {
    @Query(value = "SELECT DISTINCT ol.product_item_id, p.name, CAST(p.product_image AS VARCHAR(255)) AS product_image, p.product_rating, [pi].price\n" +
            "FROM order_line ol\n" +
            "JOIN shop_order so ON so.id = ol.order_id\n" +
            "JOIN product_item [pi] ON [pi].id = ol.product_item_id\n" +
            "JOIN product p ON p.id = [pi].pro_id\n" +
            "WHERE ol.product_item_id IN (\n" +
            "        SELECT TOP(8) ol_sub.product_item_id\n" +
            "        FROM order_line ol_sub\n" +
            "        JOIN shop_order so_sub ON so_sub.id = ol_sub.order_id\n" +
            "        GROUP BY ol_sub.product_item_id\n" +
            "        ORDER BY COUNT(DISTINCT so_sub.user_id) DESC);", nativeQuery = true)
    List<Object[]> getBestSellerProduct();
}
