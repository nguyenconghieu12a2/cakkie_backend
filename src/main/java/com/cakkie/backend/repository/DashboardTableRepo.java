package com.cakkie.backend.repository;

import com.cakkie.backend.dto.TableOrderDTO;
import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardTableRepo extends JpaRepository<shopOrder, Integer> {
    @Query(value = "select top(25) us.id,us.username, sm.name, pm.name as payment, os.status, so.order_total\n" +
            "from shop_order so\n" +
            "join user_site us on us.id = so.user_id\n" +
            "join user_payment_method upm on upm.id = so.payment_method\n" +
            "join payment_method pm on pm.id = upm.payment_type_id\n" +
            "join shipping_method sm on sm.id = so.shipping_method_id\n" +
            "join order_status os on os.id = so.order_status_id\n" +
            "order by so.order_date desc", nativeQuery = true)
    List<Object[]> tableOrder();

    @Query(value = "select top(25) us.id, us.username, us.email, COUNT(*) as total_order, sum(order_total) as total_payment\n" +
            "from shop_order so\n" +
            "join user_site us on us.id = so.user_id\n" +
            "group by us.id, us.username, us.email\n" +
            "order by total_order desc, total_payment desc", nativeQuery = true)
    List<Object[]> tableCustomer();

    @Query(value = "select sum(order_total) as [revenue] from shop_order", nativeQuery = true)
    long getRevenue();

    @Query(value = "select sum(price/2)*3 as [cost] from product_item", nativeQuery = true)
    long getCosts();

    @Query(value = "select sum(order_total) - (select sum(price/2)*3 as [cost] from product_item) as [sales] from shop_order", nativeQuery = true)
    long getSales();

    @Query(value = "select sum(qty) as [total_order] from order_line", nativeQuery = true)
    int getTotalOrder();

    @Query(value = "SELECT m.month, COALESCE(COUNT(o.arrived_date), 0) AS total_orders\n" +
            "FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN shop_order o ON MONTH(o.arrived_date) = m.month AND YEAR(o.arrived_date) = YEAR(GETDATE())\n" +
            "GROUP BY m.month\n" +
            "ORDER BY m.month;", nativeQuery = true)
    List<Object[]> chartOrder();

    @Query(value = "SELECT m.month, COUNT(u.account_create_date) AS total_users\n" +
            "FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN user_site u ON MONTH(u.account_create_date) = m.month AND YEAR(u.account_create_date) = YEAR(GETDATE())\n" +
            "GROUP BY m.month\n" +
            "ORDER BY m.month;", nativeQuery = true)
    List<Object[]> chartCustomer();

    @Query(value = "SELECT MIN(YEAR(arrived_date)) AS min_year FROM shop_order", nativeQuery = true)
    int getMinYearOrder();

    @Query(value = "SELECT MAX(YEAR(arrived_date)) AS max_year FROM shop_order", nativeQuery = true)
    int getMaxYearOrder();

    @Query(value = "SELECT MIN(YEAR(account_create_date)) AS min_year FROM user_site", nativeQuery = true)
    int getMinYearCustomer();

    @Query(value = "SELECT MAX(YEAR(account_create_date)) AS min_year FROM user_site", nativeQuery = true)
    int getMaxYearCustomer();

    @Query(value = "SELECT m.month, COALESCE(COUNT(o.arrived_date), 0) AS total_orders\n" +
            "FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN shop_order o ON MONTH(o.arrived_date) = m.month AND YEAR(o.arrived_date) = ?1\n" +
            "GROUP BY m.month\n" +
            "ORDER BY m.month;", nativeQuery = true)
    List<Object[]> chartOrderByYear(int year);

    @Query(value = "SELECT m.month, COUNT(u.account_create_date) AS total_users\n" +
            "FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN user_site u ON MONTH(u.account_create_date) = m.month AND YEAR(u.account_create_date) = ?1\n" +
            "GROUP BY m.month\n" +
            "ORDER BY m.month;", nativeQuery = true)
    List<Object[]> chartCustomerByYear(int year);
}
