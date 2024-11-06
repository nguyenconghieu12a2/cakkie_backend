package com.cakkie.backend.repository;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<orderLine, Integer> {
    @Query(value = "SELECT\n" +
            "s.id,\n" +
            "(u.firstname + ' ' + u.lastname) AS [Customer Name], \n" +
            "COUNT(o.product_item_id) AS total_product, \n" +
            "SUM(o.price) AS total_price, \n" +
            "SUM(CONVERT(int, ((o.price * d.discount_rate) / 100))) AS [total_discount_price], \n" +
            "os.[status] AS [Order Status], \n" +
            "CAST(MAX(CAST(o.note AS NVARCHAR(MAX))) AS NVARCHAR(MAX)) AS note\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN shipping_method sp ON s.shipping_method_id = sp.id\n" +
            "JOIN user_site u ON u.id = s.[user_id]\n" +
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN category c ON c.id = p.categoryID\n" +
            "JOIN discount_category dc ON c.id = dc.category_id\n" +
            "JOIN discount d ON d.id = dc.discount_id\n" +
            "GROUP BY u.firstname, u.lastname, os.[status], s.id\n" +
            "ORDER BY s.id ASC", nativeQuery = true)
    List<Object[]> getAllOrders();

    @Query(value = "SELECT \n" +
            "    o.id, \n" +
            "    (u.firstname + ' ' + u.lastname) AS fullName, \n" +
            "    p.[name] AS product_name, \n" +
            "    sp.[name] AS shipping_method,  \n" +
            "    FORMAT(s.order_date, 'dd-MM-yyyy') AS order_date,\n" +
            "    FORMAT(s.approved_date, 'dd-MM-yyyy') AS approved_date,  \n" +
            "    FORMAT(s.shipping_date, 'dd-MM-yyyy') AS shipping_date, \n" +
            "    FORMAT(s.arrived_date, 'dd-MM-yyyy') AS arrived_date, \n" +
            "    pm.[name] AS payment_method, \n" +
            "    (a.detail_address + ', ' + w.full_name_en + ', ' + di.full_name_en + ', ' + pr.full_name_en) AS full_address\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN user_site u ON u.id = s.user_id\n" +
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN category c ON c.id = p.categoryID\n" +
            "JOIN user_address ua ON ua.user_id = u.id AND ua.is_default = 1\n" +
            "JOIN address a ON ua.address_id = a.id\n" +
            "JOIN wards w ON a.wards_code = w.code\n" +
            "JOIN districts di ON a.districts_code = di.code\n" +
            "JOIN provinces pr ON di.province_code = pr.code\n" +
            "JOIN shipping_method sp ON s.shipping_method_id = sp.id\n" +
            "JOIN user_payment_method up ON u.id = up.user_id AND up.is_default = 1\n" +
            "JOIN payment_method pm ON up.payment_type_id = pm.id\n" +
            "WHERE s.id = ?1", nativeQuery = true)
    List<Object[]> getOrdersById(int id);

}
