package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
