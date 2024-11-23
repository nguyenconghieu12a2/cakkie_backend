package com.cakkie.backend.repository.adminCancelOrder;

import com.cakkie.backend.model.orderLine;
import com.cakkie.backend.model.userSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdminCancelOrderRepository extends JpaRepository<orderLine, Integer> {
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
            "    s.id,\n" +
            "    (u.firstname + ' ' + u.lastname) AS CustomerName, \n" +
            "    COUNT(DISTINCT p.id) AS total_product,\n" +
            "    s.order_total, \n" +
            "    SUM(CONVERT(int, ((o.price * d.discount_rate) / 100))) AS total_discount_price, \n" +
            "    s.canceled_date,\n" +
            "    MAX(CAST(s.canceled_reason AS NVARCHAR(MAX))) AS canceled_reason\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN user_site u ON u.id = s.user_id\n" +
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN category c ON c.id = p.categoryID\n" +
            "JOIN discount_category dc ON c.id = dc.category_id\n" +
            "JOIN discount d ON d.id = dc.discount_id\n" +
            "WHERE os.status LIKE '%Cancel%' AND u.id = ?1\n" +
            "GROUP BY \n" +
            "    s.id,\n" +
            "    u.firstname, \n" +
            "    u.lastname, \n" +
            "    os.status, \n" +
            "    s.order_total, \n" +
            "    s.canceled_date\n" +
            "ORDER BY s.id ASC", nativeQuery = true)
    List<Object[]> getDetailCancelOrderByUserId(@Param("userId") int userId);


    @Query(value = "SELECT s.id AS OrderID, (u.firstname + ' ' + u.lastname) AS CustomerName, p.name AS ProductName, i.size, o.qty AS Quantity, o.price AS Price\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN user_site u ON u.id = s.user_id\n" +
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN category c ON c.id = p.categoryID\n" +
            "WHERE os.status LIKE '%Cancel%' AND s.id = ?1\n" +
            "ORDER BY s.id ASC;\n", nativeQuery = true)
    List<Object[]> getAllProductCancelByUserId(@Param("userId") int userId);

    @Modifying
    @Query(value = "UPDATE user_site " +
            "SET status = 2, banned_reason = :bannedReason " +
            "WHERE id = :userId",
            nativeQuery = true)
    void banUser(@Param("userId") int userId, @Param("bannedReason") String bannedReason);

    @Query("SELECT u FROM userSite u WHERE u.id = :userId")
    Optional<userSite> findUserById(@Param("userId") int userId);
}
