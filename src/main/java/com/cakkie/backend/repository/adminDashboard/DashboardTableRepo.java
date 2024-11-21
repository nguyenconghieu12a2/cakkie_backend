package com.cakkie.backend.repository.adminDashboard;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardTableRepo extends JpaRepository<shopOrder, Integer> {
    @Query(value = "select top(30) us.id,us.username, sm.name, pm.name as payment, os.status, so.order_total\n" +
            "from shop_order so\n" +
            "join user_site us on us.id = so.user_id\n" +
            "join user_payment_method upm on upm.id = so.payment_method\n" +
            "join payment_method pm on pm.id = upm.payment_type_id\n" +
            "join shipping_method sm on sm.id = so.shipping_method_id\n" +
            "join order_status os on os.id = so.order_status_id\n" +
            "order by so.order_date desc", nativeQuery = true)
    List<Object[]> tableOrder();

    @Query(value = "select top(30) us.id, us.username, us.email, COUNT(*) as total_order, sum(order_total) as total_payment\n" +
            "from shop_order so\n" +
            "join user_site us on us.id = so.user_id\n" +
            "group by us.id, us.username, us.email\n" +
            "order by total_payment desc, total_order desc", nativeQuery = true)
    List<Object[]> tableCustomer();

    @Query(value = "select COALESCE(sum(order_total), 0) as [revenue] from shop_order", nativeQuery = true)
    long getRevenue();

    @Query(value = "select COALESCE(sum(price/2)*3, 0) as [cost] from product_item", nativeQuery = true)
    long getCosts();

    @Query(value = "select COALESCE(sum(order_total) - (select sum(price/2)*3 from product_item), 0) as [sales] from shop_order", nativeQuery = true)
    long getSales();

    @Query(value = "select COALESCE(sum(qty), 0) as [total_order] from order_line", nativeQuery = true)
    int getTotalOrder();

    //DAYS
    @Query(value = "SELECT m.month, d.day, COALESCE(COUNT(o.arrived_date), 0) AS total_orders\n" +
            "FROM \n" +
            "    (SELECT n AS day FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), \n" +
            "                                  (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23), \n" +
            "                                  (24), (25), (26), (27), (28), (29), (30), (31)) AS Numbers(n)) AS d\n" +
            "CROSS JOIN (SELECT m AS month FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS Numbers(m)) AS m\n" +
            "LEFT JOIN shop_order o \n" +
            "    ON DAY(o.arrived_date) = d.day\n" +
            "    AND MONTH(o.arrived_date) = m.month\n" +
            "    AND YEAR(o.arrived_date) = ?2\n" +
            "WHERE d.day <= \n" +
            "    CASE \n" +
            "        WHEN m.month IN (1, 3, 5, 7, 8, 10, 12) THEN 31\n" +
            "        WHEN m.month IN (4, 6, 9, 11) THEN 30\n" +
            "        WHEN m.month = 2 AND (?2 % 4 = 0 AND (?2 % 100 <> 0 OR ?2 % 400 = 0)) THEN 29\n" +
            "        WHEN m.month = 2 THEN 28\n" +
            "    END\n" +
            "GROUP BY m.month, d.day\n" +
            "HAVING m.month = ?1\n" +
            "ORDER BY m.month, d.day;", nativeQuery = true)
    List<Object[]> chartOrderDay(int month, int year);

    @Query(value = "SELECT m.month, d.day, COALESCE(COUNT(u.account_create_date), 0) AS total_users\n" +
            "FROM \n" +
            "    (SELECT n AS day FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), \n" +
            "                                  (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23), \n" +
            "                                  (24), (25), (26), (27), (28), (29), (30), (31)) AS Numbers(n)) AS d\n" +
            "CROSS JOIN (SELECT m AS month FROM (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS Numbers(m)) AS m\n" +
            "LEFT JOIN user_site u \n" +
            "    ON DAY(u.account_create_date) = d.day\n" +
            "    AND MONTH(u.account_create_date) = m.month\n" +
            "    AND YEAR(u.account_create_date) = ?2\n" +
            "WHERE d.day <= \n" +
            "    CASE \n" +
            "        WHEN m.month IN (1, 3, 5, 7, 8, 10, 12) THEN 31\n" +
            "        WHEN m.month IN (4, 6, 9, 11) THEN 30\n" +
            "        WHEN m.month = 2 AND (?2 % 4 = 0 AND (?2 % 100 <> 0 OR ?2 % 400 = 0)) THEN 29\n" +
            "        WHEN m.month = 2 THEN 28\n" +
            "    END\n" +
            "GROUP BY m.month, d.day\n" +
            "HAVING m.month = ?1\n" +
            "ORDER BY m.month, d.day;", nativeQuery = true)
    List<Object[]> chartCustomerDay(int month, int year);

    //MONTHS
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

    @Query(value = "SELECT COALESCE(MIN(YEAR(arrived_date)), YEAR(GETDATE())) AS min_year FROM shop_order", nativeQuery = true)
    int getMinYearOrder();

    @Query(value = "SELECT COALESCE(MAX(YEAR(arrived_date)), YEAR(GETDATE())) AS max_year FROM shop_order", nativeQuery = true)
    int getMaxYearOrder();

    @Query(value = "SELECT COALESCE(MIN(YEAR(account_create_date)), YEAR(GETDATE())) AS min_year FROM user_site", nativeQuery = true)
    int getMinYearCustomer();

    @Query(value = "SELECT COALESCE(MAX(YEAR(account_create_date)), YEAR(GETDATE())) AS max_year FROM user_site", nativeQuery = true)
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

    //QUARTERS
    @Query(value = "SELECT \n" +
            "    CASE \n" +
            "        WHEN m.month BETWEEN 1 AND 3 THEN 'Q1'\n" +
            "        WHEN m.month BETWEEN 4 AND 6 THEN 'Q2'\n" +
            "        WHEN m.month BETWEEN 7 AND 9 THEN 'Q3'\n" +
            "        WHEN m.month BETWEEN 10 AND 12 THEN 'Q4'\n" +
            "    END AS quarter,\n" +
            "    COUNT(o.arrived_date) AS total_orders\n" +
            "FROM \n" +
            "    (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN \n" +
            "    shop_order o ON MONTH(o.arrived_date) = m.month \n" +
            "    AND YEAR(o.arrived_date) = ?1\n" +
            "GROUP BY \n" +
            "    CASE \n" +
            "        WHEN m.month BETWEEN 1 AND 3 THEN 'Q1'\n" +
            "        WHEN m.month BETWEEN 4 AND 6 THEN 'Q2'\n" +
            "        WHEN m.month BETWEEN 7 AND 9 THEN 'Q3'\n" +
            "        WHEN m.month BETWEEN 10 AND 12 THEN 'Q4'\n" +
            "    END\n" +
            "ORDER BY \n" +
            "    quarter;", nativeQuery = true)
    List<Object[]> chartOrderByQuarter(int year);

    @Query(value = "SELECT \n" +
            "    CASE \n" +
            "        WHEN m.month BETWEEN 1 AND 3 THEN 'Q1'\n" +
            "        WHEN m.month BETWEEN 4 AND 6 THEN 'Q2'\n" +
            "        WHEN m.month BETWEEN 7 AND 9 THEN 'Q3'\n" +
            "        WHEN m.month BETWEEN 10 AND 12 THEN 'Q4'\n" +
            "    END AS quarter,\n" +
            "    COUNT(u.account_create_date) AS total_users\n" +
            "FROM \n" +
            "    (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS m(month)\n" +
            "LEFT JOIN \n" +
            "    user_site u ON MONTH(u.account_create_date) = m.month \n" +
            "    AND YEAR(u.account_create_date) = ?1\n" +
            "GROUP BY \n" +
            "    CASE \n" +
            "        WHEN m.month BETWEEN 1 AND 3 THEN 'Q1'\n" +
            "        WHEN m.month BETWEEN 4 AND 6 THEN 'Q2'\n" +
            "        WHEN m.month BETWEEN 7 AND 9 THEN 'Q3'\n" +
            "        WHEN m.month BETWEEN 10 AND 12 THEN 'Q4'\n" +
            "    END\n" +
            "ORDER BY \n" +
            "    quarter;", nativeQuery = true)
    List<Object[]> chartCustomerByQuarter(int year);

    //YEARS
    @Query(value = "SELECT y.year, COALESCE(COUNT(o.arrived_date), 0) AS total_orders\n" +
            "FROM (\n" +
            "    SELECT MIN_YEAR + n AS year\n" +
            "    FROM (\n" +
            "        SELECT COALESCE(MIN(YEAR(arrived_date)), YEAR(GETDATE())) AS MIN_YEAR,\n" +
            "               COALESCE(MAX(YEAR(arrived_date)), YEAR(GETDATE())) AS MAX_YEAR\n" +
            "        FROM shop_order\n" +
            "    ) AS YearBounds\n" +
            "    CROSS APPLY (\n" +
            "        VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), \n" +
            "               (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23), (24), \n" +
            "               (25), (26), (27), (28), (29), (30), (31), (32), (33), (34), (35), (36), \n" +
            "               (37), (38), (39), (40), (41), (42), (43), (44), (45), (46), (47), (48), \n" +
            "               (49), (50), (51), (52), (53), (54), (55), (56), (57), (58), (59), (60)\n" +
            "    ) AS N(n)\n" +
            "    WHERE MIN_YEAR + n <= (SELECT MAX_YEAR FROM (SELECT COALESCE(MAX(YEAR(arrived_date)), YEAR(GETDATE())) AS MAX_YEAR FROM shop_order) AS MaxYear)\n" +
            ") AS y\n" +
            "LEFT JOIN shop_order o ON YEAR(o.arrived_date) = y.year\n" +
            "GROUP BY y.year\n" +
            "ORDER BY y.year;", nativeQuery = true)
    List<Object[]> chartOrderYear();

    @Query(value = "SELECT y.year, COALESCE(COUNT(u.account_create_date), 0) AS total_users\n" +
            "FROM (\n" +
            "    SELECT MIN_YEAR + n AS year\n" +
            "    FROM (\n" +
            "        SELECT COALESCE(MIN(YEAR(account_create_date)), YEAR(GETDATE())) AS MIN_YEAR,\n" +
            "               COALESCE(MAX(YEAR(account_create_date)), YEAR(GETDATE())) AS MAX_YEAR\n" +
            "        FROM user_site\n" +
            "    ) AS YearBounds\n" +
            "    CROSS APPLY (\n" +
            "        VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), \n" +
            "               (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23), (24), \n" +
            "               (25), (26), (27), (28), (29), (30), (31), (32), (33), (34), (35), (36), \n" +
            "               (37), (38), (39), (40), (41), (42), (43), (44), (45), (46), (47), (48), \n" +
            "               (49), (50), (51), (52), (53), (54), (55), (56), (57), (58), (59), (60)\n" +
            "    ) AS N(n)\n" +
            "    WHERE MIN_YEAR + n <= (SELECT MAX_YEAR FROM (SELECT COALESCE(MAX(YEAR(account_create_date)), YEAR(GETDATE())) AS MAX_YEAR FROM user_site) AS MaxYear)\n" +
            ") AS y\n" +
            "LEFT JOIN user_site u ON YEAR(u.account_create_date) = y.year\n" +
            "GROUP BY y.year\n" +
            "ORDER BY y.year;", nativeQuery = true)
    List<Object[]> chartCustomerYear();
}
