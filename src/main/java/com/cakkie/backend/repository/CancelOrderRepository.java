package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CancelOrderRepository extends JpaRepository<orderLine, Integer> {
    @Query(value = "SELECT \n" +
            "    u.id AS [User ID], \n" +
            "    (u.firstname + ' ' + u.lastname) AS [Customer Name], \n" +
            "    COUNT(s.id) AS [Total Cancel]\n" +
            "FROM \n" +
            "    shop_order s\n" +
            "JOIN \n" +
            "    user_site u ON u.id = s.user_id\n" +
            "JOIN \n" +
            "    order_status os ON s.order_status_id = os.id\n" +
            "WHERE \n" +
            "    os.status LIKE '%Cancel%'\n" +
            "GROUP BY \n" +
            "    u.id, u.firstname, u.lastname", nativeQuery = true)
    List<Object[]> getAllCancelOrder();

    @Query(value = "SELECT\n" +
            "s.id,\n" +
            "(u.firstname + ' ' + u.lastname) AS CustomerName, \n" + // Removed brackets
            "COUNT(o.product_item_id) AS total_product,\n" +
            "s.order_total, \n" +
            "SUM(CONVERT(int, ((o.price * d.discount_rate) / 100))) AS total_discount_price, \n" + // Removed brackets
            "os.status AS OrderStatus \n" + // Removed brackets
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN user_site u ON u.id = s.user_id\n" + // Removed brackets
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN category c ON c.id = p.categoryID\n" +
            "JOIN discount_category dc ON c.id = dc.category_id\n" +
            "JOIN discount d ON d.id = dc.discount_id\n" +
            "WHERE os.status LIKE '%Cancel%' AND u.id = :userId\n" +
            "GROUP BY u.firstname, u.lastname, os.status, s.id, s.order_total\n" +
            "ORDER BY s.id ASC", nativeQuery = true)
    List<Object[]> getDetailCancelOrderByUserId(@Param("userId") int userId);

}
