package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BestSellerProductRepo extends JpaRepository<orderLine, Integer> {
    @Query(value = "WITH RankedProducts AS (\n" +
            "    SELECT \n" +
            "        --ol.product_item_id, \n" +
            "        p.id AS product_id, \n" +
            "        p.name, \n" +
            "        CAST(p.product_image AS VARCHAR(255)) AS product_image, -- Convert IMAGE or TEXT to VARCHAR\n" +
            "        p.product_rating, \n" +
            "        pi.price,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY p.id ORDER BY COUNT(DISTINCT so.user_id) DESC) AS row_num\n" +
            "    FROM order_line ol\n" +
            "    JOIN shop_order so ON so.id = ol.order_id\n" +
            "    JOIN product_item pi ON pi.id = ol.product_item_id\n" +
            "    JOIN product p ON p.id = pi.pro_id\n" +
            "    WHERE ol.product_item_id IN (\n" +
            "        SELECT TOP(100) PERCENT ol_sub.product_item_id -- Allow sorting with TOP\n" +
            "        FROM order_line ol_sub\n" +
            "        JOIN shop_order so_sub ON so_sub.id = ol_sub.order_id\n" +
            "        GROUP BY ol_sub.product_item_id\n" +
            "        ORDER BY COUNT(DISTINCT so_sub.user_id) DESC\n" +
            "    )\n" +
            "    GROUP BY ol.product_item_id, p.id, p.name, CAST(p.product_image AS VARCHAR(255)), p.product_rating, pi.price\n" +
            "),\n" +
            "UniqueProducts AS (\n" +
            "    SELECT \n" +
            "        --product_item_id, \n" +
            "        product_id, \n" +
            "        name, \n" +
            "        product_image, \n" +
            "        product_rating, \n" +
            "        price\n" +
            "    FROM RankedProducts\n" +
            "    WHERE row_num = 1 -- Get only the first occurrence for each product ID\n" +
            ")\n" +
            "SELECT TOP 8 \n" +
            "    --product_item_id, \n" +
            "    product_id, \n" +
            "    name, \n" +
            "    product_image, \n" +
            "    product_rating, \n" +
            "    price\n" +
            "FROM UniqueProducts;", nativeQuery = true)
    List<Object[]> getBestSellerProduct();
}
