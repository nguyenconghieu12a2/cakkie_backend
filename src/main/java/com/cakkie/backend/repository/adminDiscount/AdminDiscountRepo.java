package com.cakkie.backend.repository.adminDiscount;

import com.cakkie.backend.model.discountCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminDiscountRepo extends JpaRepository<discountCategory, Integer> {
    @Query(value = "WITH CategoryWithIsDeleted AS (\n" +
            "    SELECT \n" +
            "        c.id AS id, \n" +
            "        c.cate_name AS cate_name,\n" +
            "        d.name AS current_discount, \n" +
            "        d.discount_rate AS discount_percent,\n" +
            "        dc.is_deleted,\n" +
            "        ROW_NUMBER() OVER (\n" +
            "            PARTITION BY c.id \n" +
            "            ORDER BY dc.is_deleted DESC, d.discount_rate DESC\n" +
            "        ) AS discount_priority\n" +
            "    FROM category c\n" +
            "    LEFT JOIN discount_category dc ON c.id = dc.category_id\n" +
            "    LEFT JOIN discount d ON d.id = dc.discount_id\n" +
            "    WHERE c.id IN (\n" +
            "        SELECT id \n" +
            "        FROM category \n" +
            "        WHERE id NOT IN (\n" +
            "            SELECT DISTINCT parent_id \n" +
            "            FROM category \n" +
            "            WHERE parent_id IS NOT NULL\n" +
            "        )\n" +
            "    )\n" +
            ")\n" +
            "SELECT \n" +
            "    id, \n" +
            "    cate_name,\n" +
            "    CASE WHEN is_deleted = 0 THEN '' ELSE COALESCE(current_discount, '') END AS discount_name,\n" +
            "    CASE WHEN is_deleted = 0 THEN 0 ELSE COALESCE(discount_percent, 0) END AS discount_rate,\n" +
            "    is_deleted\n" +
            "FROM CategoryWithIsDeleted\n" +
            "WHERE discount_priority = 1\n" +
            "ORDER BY id;", nativeQuery = true)
    List<Object[]> getCateDiscountList();

    @Query(value = "select distinct dc.id, dc.category_id, c.cate_name, dc.is_deleted\n" +
            "from discount_category dc\n" +
            "join category c on c.id = dc.category_id\n" +
            "where dc.discount_id in \n" +
            "(select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1) and dc.discount_id = ?1", nativeQuery = true)
    List<Object[]> getCateAppliedCommonDiscountById(int id);

    @Query(value = "select distinct c.id, c.cate_name\n" +
            "from discount_category dc\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where c.id not in (select distinct parent_id from category where parent_id is not null)\n" +
            "and c.id not in \n" +
            "(select distinct dc.category_id\n" +
            "from discount_category dc\n" +
            "join category c on c.id = dc.category_id\n" +
            "where dc.discount_id = ?1)", nativeQuery = true)
    List<Object[]> getCateNOTAppliedCommonDiscountById(int id);

    //discount by cate activate (wherether it's common or discrete)
    @Query(value = "select dc.id, dc.discount_id, dc.category_id, \n" +
            "\td.name, d.description, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'yyyy-MM-dd HH:mm:ss') as [start_date], FORMAT(d.end_date, 'yyyy-MM-dd HH:mm:ss') as [end_date] \n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1 and dc.is_deleted = 1", nativeQuery = true)
    List<Object[]> getDiscountByCategoryActivate(int id);

    //discrete discount by cate inactivate
    @Query(value = "select dc.id, dc.discount_id, dc.category_id, \n" +
            "\td.name, d.description, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'yyyy-MM-dd HH:mm:ss') as [start_date], FORMAT(d.end_date, 'yyyy-MM-dd HH:mm:ss') as [end_date] \n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1\n" +
            "and dc.discount_id not in \n" +
            "(select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1) and dc.is_deleted = 0", nativeQuery = true)
    List<Object[]> getDiscountByCategoryInactivate(int id);

    //common discount by cate inactivate
    @Query(value = "select dc.id, dc.discount_id, dc.category_id, \n" +
            "\td.name, d.description, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'yyyy-MM-dd HH:mm:ss') as [start_date], FORMAT(d.end_date, 'yyyy-MM-dd HH:mm:ss') as [end_date]  \n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1\n" +
            "and dc.discount_id in \n" +
            "(select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1) and dc.is_deleted = 0", nativeQuery = true)
    List<Object[]> getCommonDiscountByCategoryInactivate(int id);

    //remove current activate cateDiscount
    @Modifying
    @Query(value = "update discount_category set is_deleted = 0  where id = \n" +
            "(select dc.id\n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1 and dc.is_deleted = 1)", nativeQuery = true)
    void removeCurrentDiscount(int id);

    //replace new discount to current discount
    @Modifying
    @Query(value = "update discount_category set is_deleted = 1 where id = ?1", nativeQuery = true)
    void replaceCurrentDiscount(int id);

}
