package com.cakkie.backend.repository.adminDiscount;

import com.cakkie.backend.model.discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminCommonDiscountRepo extends JpaRepository<discount, Integer> {
    @Query(value = "select id, name, discount_rate, \n" +
            "\tFORMAT(start_date, 'yyyy-MM-dd HH:mm:ss') as [start_date], \n" +
            "\tFORMAT(end_date, 'yyyy-MM-dd HH:mm:ss') as [end_date] from discount\n" +
            "where id in (select discount_id from discount_category\n" +
            "group by discount_id\n" +
            "having count(*) > 1)", nativeQuery = true)
    List<Object[]> getCommonDiscountList();

    @Query(value = "SELECT id, name, discount_rate, description, " +
            "FORMAT(start_date, 'yyyy-MM-dd HH:mm:ss') AS start_date, " +
            "FORMAT(end_date, 'yyyy-MM-dd HH:mm:ss') AS end_date " +
            "FROM discount WHERE id = ?1", nativeQuery = true)
    List<Object[]> getDetailCommonDiscountById(int id);

    //get created discount id
    @Query(value = "select max(id) as createdID from discount", nativeQuery = true)
    int getCreatedID();

}
