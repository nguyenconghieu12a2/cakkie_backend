package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelOrderRepository extends JpaRepository<orderLine, Integer> {
    @Query(value = "SELECT u.id AS [User ID], (u.firstname + ' ' + u.lastname) AS [Customer Name], COUNT(*) AS [Total Cancel]\n" +
            "FROM order_line o\n" +
            "JOIN shop_order s ON o.order_id = s.id\n" +
            "JOIN user_site u ON u.id = s.[user_id]\n" +
            "JOIN order_status os ON s.order_status_id = os.id\n" +
            "WHERE os.status LIKE '%Cancel%'\n" +
            "GROUP BY u.id, u.firstname, u.lastname", nativeQuery = true)
    List<Object[]> getAllCancelOrder();
}
