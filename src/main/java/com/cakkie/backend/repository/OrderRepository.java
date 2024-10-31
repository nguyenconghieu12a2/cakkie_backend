package com.cakkie.backend.repository;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<orderLine, Integer> {
    @Query("SELECT new com.cakkie.backend.dto.OrderDTO(" +
            "o.id, CONCAT(u.firstname, ' ', u.lastname), o.price, " +
            "CAST((o.price * d.discountRate / 100) AS integer), os.status, o.note) " +
            "FROM orderLine o " +
            "JOIN o.orderId s " +
            "JOIN s.userId u " +
            "JOIN s.orderStatusId os " +
            "JOIN o.productItemId i " +
            "JOIN i.proId p " +
            "JOIN p.categoryID c " +
            "JOIN c.discountCategoryList dc " +
            "JOIN dc.discountId d")
    List<OrderDTO> getAllOrders();

    @Query(value = "SELECT o.id, (u.firstname + u.lastname) AS fullName, p.[name], sp.[name],  FORMAT(s.order_date, 'dd-MM-yyyy') AS [order_date] ,FORMAT(s.approved_date, 'dd-MM-yyyy') AS [approved_date],  FORMAT(s.shipping_date, 'dd-MM-yyyy') AS [shipping_date], FORMAT(s.arrived_date, 'dd-MM-yyyy') AS [arrived_date], pm.[name], (a.detail_address+ ',' +w.full_name_en + ', ' + d.full_name_en + ', ' +pr.full_name_en) AS address\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN payment_method pm ON s.payment_method = pm.id\n" +
            "JOIN order_status st ON s.order_status_id = st.id\n" +
            "JOIN shipping_method sp ON s.shipping_method_id = sp.id\n" +
            "JOIN user_site u ON u.id = s.[user_id]\n" +
            "JOIN product_item i ON o.product_item_id = i.id\n" +
            "JOIN product p ON i.pro_id = p.id\n" +
            "JOIN user_address ua ON u.id = ua.[user_id]\n" +
            "JOIN [address] a ON ua.address_id = a.id\n" +
            "JOIN districts d ON a.districts_code = d.code\n" +
            "JOIN wards w ON a.wards_code = w.code\n" +
            "JOIN provinces pr ON d.province_code = pr.code\n" +
            "WHERE o.id = ?1", nativeQuery = true)
    List<Object[]> getOrdersById(int id);
}
