package com.cakkie.backend.repository.adminDiscount;

import com.cakkie.backend.model.discountCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminDiscountRepo extends JpaRepository<discountCategory, Integer> {
    @Query(value = "select c.id, c.cate_name, \n" +
            "\tCOALESCE(d.name, '') as current_discount, COALESCE(d.discount_rate, 0) as discount_percent\n" +
            "from discount_category dc \n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where c.id in \n" +
            "(select id from category where id not in (select distinct parent_id from category where parent_id is not null)) \n" +
            "and (dc.is_deleted = 1 or dc.is_deleted is null)", nativeQuery = true)
    List<Object[]> getCateDiscountList();

    @Query(value = "select distinct dc.category_id, c.cate_name\n" +
            "from discount_category dc\n" +
            "join category c on c.id = dc.category_id\n" +
            "where dc.discount_id in \n" +
            "(select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1) and dc.discount_id = ?1", nativeQuery = true)
    List<Object[]> getCateAppliedCommonDiscountById(int id);

    @Query(value = "select distinct dc.category_id, c.cate_name\n" +
            "from discount_category dc\n" +
            "join category c on c.id = dc.category_id\n" +
            "where dc.category_id not in \n" +
            "(select distinct dc.category_id\n" +
            "from discount_category dc\n" +
            "join category c on c.id = dc.category_id\n" +
            "where dc.discount_id = ?1)", nativeQuery = true)
    List<Object[]> getCateNOTAppliedCommonDiscountById(int id);

    //discount by cate activate (wherether it's common or discrete)
    @Query(value = "select dc.id, dc.discount_id, dc.category_id, \n" +
            "\td.name, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'dd-MM-yyyy hh-mm-ss') as [start_date], FORMAT(d.end_date, 'dd-MM-yyyy hh-mm-ss') as [end_date] \n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1 and dc.is_deleted = 1", nativeQuery = true)
    List<Object[]> getDiscountByCategoryActivate(int id);

    //discrete discount by cate inactivate
    @Query(value = "select dc.id, dc.discount_id, dc.category_id, \n" +
            "\td.name, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'dd-MM-yyyy hh-mm-ss') as [start_date], FORMAT(d.end_date, 'dd-MM-yyyy hh-mm-ss') as [end_date] \n" +
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
            "\td.name, d.discount_rate, \n" +
            "\tFORMAT(d.start_date, 'dd-MM-yyyy hh-mm-ss') as [start_date], FORMAT(d.end_date, 'dd-MM-yyyy hh-mm-ss') as [end_date]  \n" +
            "from discount_category dc\n" +
            "join discount d on d.id = dc.discount_id\n" +
            "right join category c on c.id = dc.category_id\n" +
            "where dc.category_id = ?1\n" +
            "and dc.discount_id in \n" +
            "(select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1) and dc.is_deleted = 0", nativeQuery = true)
    List<Object[]> getCommonDiscountByCategoryInactivate(int id);

}
