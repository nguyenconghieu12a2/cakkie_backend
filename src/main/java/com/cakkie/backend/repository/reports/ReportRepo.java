package com.cakkie.backend.repository.reports;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepo extends JpaRepository<shopOrder, Integer> {

    @Query(value = "SELECT\n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(DISTINCT so.user_id) AS [customers],\n" +
            "    COUNT(so.id) AS [orders],\n" +
            "    SUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so\n" +
            "JOIN user_site us ON us.id = so.user_id\n" +
            "WHERE so.arrived_date IS NOT NULL\n" +
            "GROUP BY \n" +
            "    DATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "    DATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "    DATEPART(YEAR, DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)),\n" +
            "    DATEPART(WEEK, DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC;", nativeQuery = true)
    List<Object[]> getCustomerOrders();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tCOUNT(DISTINCT so.user_id) AS [customers],\n" +
            "    COUNT(*) AS [orders],\n" +
            "\tSUM(so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN user_site us ON us.id = so.user_id AND so.arrived_date BETWEEN ?1 AND ?2  \n" +
            "GROUP BY DATEPART(YEAR, so.arrived_date), DATEPART(WEEK, so.arrived_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getCustomerOrdersFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(DISTINCT so.id) AS [orders],\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id\n" +
            "WHERE so.arrived_date IS NOT NULL\n" +
            "GROUP BY \n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getOrders();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(DISTINCT so.id) AS [orders],\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id AND so.arrived_date BETWEEN ?1 AND ?2\n" +
            "GROUP BY DATEPART(YEAR, so.arrived_date), DATEPART(WEEK, so.arrived_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getOrdersFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    sm.name, \n" +
            "    COUNT(so.id) AS orders, \n" +
            "    COUNT(so.id) * sm.price AS total\n" +
            "FROM shop_order so\n" +
            "RIGHT JOIN shipping_method sm ON sm.id = so.shipping_method_id\n" +
            "WHERE so.canceled_date IS NULL\n" +
            "GROUP BY sm.name, sm.price\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getShipping();

    @Query(value = "SELECT \n" +
            "    sm.name, \n" +
            "    COUNT(so.id) AS orders, \n" +
            "    COUNT(so.id) * sm.price AS total\n" +
            "FROM shop_order so\n" +
            "RIGHT JOIN shipping_method sm ON sm.id = so.shipping_method_id\n" +
            "AND so.arrived_date BETWEEN ?1 AND ?2 \n" +
            "WHERE so.canceled_date IS NULL\n" +
            "GROUP BY sm.name, sm.price\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getShippingFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.canceled_date), so.canceled_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(DISTINCT so.id) AS [cancel_orders],\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id\n" +
            "WHERE so.canceled_date IS NOT NULL\n" +
            "GROUP BY \n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)),\n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date), so.canceled_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date), so.canceled_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)) ASC", nativeQuery = true)
    List<Object[]> getCancelOrder();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.canceled_date), so.canceled_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(DISTINCT so.id) AS [cancel_orders],\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id AND so.canceled_date BETWEEN ?1 AND ?2\n" +
            "WHERE so.canceled_date IS NOT NULL\n" +
            "GROUP BY DATEPART(YEAR, so.canceled_date), DATEPART(WEEK, so.canceled_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.canceled_date) + 1, so.canceled_date)) ASC", nativeQuery = true)
    List<Object[]> getCancelOrderFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tSUM(so.order_total) AS [revenue]\n" +
            "FROM shop_order so \n" +
            "WHERE so.arrived_date IS NOT NULL\n" +
            "GROUP BY \n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getSales();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tSUM(so.order_total) AS [revenue]\n" +
            "FROM shop_order so \n" +
            "WHERE so.arrived_date BETWEEN ?1 AND ?2\n" +
            "GROUP BY DATEPART(YEAR, so.arrived_date), DATEPART(WEEK, so.arrived_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getSalesFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    c.name, \n" +
            "    COUNT(so.id) AS orders, \n" +
            "    COUNT(so.id) * c.price_discount AS total\n" +
            "FROM shop_order so\n" +
            "RIGHT JOIN coupons c ON c.id = so.coupons\n" +
            "WHERE so.canceled_date IS NULL\n" +
            "GROUP BY c.name, c.price_discount\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getCoupons();

    @Query(value = "SELECT \n" +
            "    c.name, \n" +
            "    COUNT(so.id) AS orders, \n" +
            "    COUNT(so.id) * c.price_discount AS total\n" +
            "FROM shop_order so\n" +
            "RIGHT JOIN coupons c ON c.id = so.coupons\n" +
            "AND so.arrived_date BETWEEN ?1 AND ?2  \n" +
            "WHERE so.canceled_date IS NULL\n" +
            "GROUP BY c.name, c.price_discount\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getCouponsFilter(String startDate, String endDate);

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(ol.qty) AS [quantity],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id\n" +
            "WHERE so.arrived_date IS NOT NULL\n" +
            "GROUP BY \n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)),\n" +
            "\tDATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)),\n" +
            "\tDATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getProductSales();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, so.arrived_date), so.arrived_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tCOUNT(ol.order_id) AS [products],\n" +
            "\tSUM(ol.qty) AS [quantity],\n" +
            "\tSUM(DISTINCT so.order_total) AS [total]\n" +
            "FROM shop_order so \n" +
            "JOIN order_line ol ON ol.order_id = so.id AND so.arrived_date BETWEEN ?1 AND ?2\n" +
            "GROUP BY DATEPART(YEAR, so.arrived_date), DATEPART(WEEK, so.arrived_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, so.arrived_date) + 1, so.arrived_date)) ASC", nativeQuery = true)
    List<Object[]> getProductSalesFilter(String startDate, String endDate);
}
