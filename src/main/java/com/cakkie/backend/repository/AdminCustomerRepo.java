package com.cakkie.backend.repository;

import com.cakkie.backend.model.userSite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminCustomerRepo extends JpaRepository<userSite, Integer> {
    @Query(value = "select us.id, us.firstname, us.lastname, us.username, us.gender, FORMAT(us.birthday, 'dd-MM-yyyy') AS birthday, us.email, us.phone,FORMAT(us.account_create_date, 'dd-MM-yyyy') AS account_create_date,\t\n" +
            "\t(a.detail_address + ', ' +\tw.full_name + ', ' + d.full_name + ', ' + p.full_name) as customer_address, ust.status\n" +
            "from user_site us\n" +
            "join user_address ua on ua.user_id = us.id\n" +
            "join address a on a.id = ua.address_id\n" +
            "join districts d on d.code = a.districts_code\n" +
            "join provinces p on p.code = d.province_code\n" +
            "join wards w on w.code = a.wards_code\n" +
            "join user_status ust on ust.id = us.status\n" +
            "where us.status = 1", nativeQuery = true)
    List<Object[]> getAllCustomer();

    @Query(value = "select us.id, us.firstname, us.lastname, us.username, us.gender, FORMAT(us.birthday, 'dd-MM-yyyy') AS birthday, us.email, us.phone,FORMAT(us.account_create_date, 'dd-MM-yyyy') AS account_create_date,\t\n" +
            "\t(a.detail_address + ', ' +\tw.full_name + ', ' + d.full_name + ', ' + p.full_name) as customer_address, ust.status\n" +
            "from user_site us\n" +
            "join user_address ua on ua.user_id = us.id\n" +
            "join address a on a.id = ua.address_id\n" +
            "join districts d on d.code = a.districts_code\n" +
            "join provinces p on p.code = d.province_code\n" +
            "join wards w on w.code = a.wards_code\n" +
            "join user_status ust on ust.id = us.status\n" +
            "where us.status = 1 and us.id = ?1", nativeQuery = true)
    List<Object[]> getCustomerById(int id);

    @Query(value = "select us.id, us.firstname, us.lastname, us.username, us.gender, FORMAT(us.birthday, 'dd-MM-yyyy') AS birthday, us.email, us.phone,FORMAT(us.account_create_date, 'dd-MM-yyyy') AS account_create_date,\t\n" +
            "\t(a.detail_address + ', ' +\tw.full_name + ', ' + d.full_name + ', ' + p.full_name) as customer_address, ust.status\n" +
            "from user_site us\n" +
            "join user_address ua on ua.user_id = us.id\n" +
            "join address a on a.id = ua.address_id\n" +
            "join districts d on d.code = a.districts_code\n" +
            "join provinces p on p.code = d.province_code\n" +
            "join wards w on w.code = a.wards_code\n" +
            "join user_status ust on ust.id = us.status\n" +
            "where us.status = 2", nativeQuery = true)
    List<Object[]> getAllBannedCustomer();

    @Query(value = "select us.id, us.firstname, us.lastname, us.username, us.gender, FORMAT(us.birthday, 'dd-MM-yyyy') AS birthday, us.email, us.phone,FORMAT(us.account_create_date, 'dd-MM-yyyy') AS account_create_date,\n" +
            "(a.detail_address + ', ' + w.full_name + ', ' + d.full_name + ', ' + p.full_name) as customer_address, ust.status, us.banned_reason\n" +
            "from user_site us\n" +
            "join user_address ua on ua.user_id = us.id\n" +
            "join address a on a.id = ua.address_id\n" +
            "join districts d on d.code = a.districts_code\n" +
            "join provinces p on p.code = d.province_code\n" +
            "join wards w on w.code = a.wards_code\n" +
            "join user_status ust on ust.id = us.status\n" +
            "where us.id = ?1 and us.status = 2", nativeQuery = true)
    List<Object[]> getBannedCustomerById(int id);

    @Query(value = "select us.id, us.firstname, us.lastname, us.username, us.gender, FORMAT(us.birthday, 'dd-MM-yyyy') AS birthday, us.email, us.phone,FORMAT(us.account_create_date, 'dd-MM-yyyy') AS account_create_date,\t\n" +
            "\t(a.detail_address + ', ' +\tw.full_name + ', ' + d.full_name + ', ' + p.full_name) as customer_address, ust.status\n" +
            "from user_site us\n" +
            "join user_address ua on ua.user_id = us.id\n" +
            "join address a on a.id = ua.address_id\n" +
            "join districts d on d.code = a.districts_code\n" +
            "join provinces p on p.code = d.province_code\n" +
            "join wards w on w.code = a.wards_code\n" +
            "join user_status ust on ust.id = us.status\n" +
            "where us.status = 3", nativeQuery = true)
    List<Object[]> getAllDeletedCustomer();

    @Transactional
    @Modifying
    @Query(value = "update user_site set firstname = ?2, lastname = ?3 where id = ?1", nativeQuery = true)
    void updateCustomerInfo(int id, String firstname, String lastname);

    @Modifying
    @Query(value = "update user_site set status = 3 where id = :id", nativeQuery = true)
    void deleteCustomerById(@Param("id") int id);

    @Modifying
    @Query(value = "update user_site set status = 1, banned_reason = '' where id = :id", nativeQuery = true)
    void recoverCustomerById(@Param("id") int id);

    @Modifying
    @Query(value = "update user_site set status = 2, banned_reason = ?2 where id = ?1", nativeQuery = true)
    void bannedCustomerById(int id, String bannedReason);

    @Query(value = "select COUNT(*) as processing_order from shop_order\n" +
            "where order_status_id IN (1,2,3) and user_id = ?1", nativeQuery = true)
    int getCustomerProcessdingOrder(int id);

    @Query(value = "select COUNT(*) as processing_order from shop_order\n" +
            "where order_status_id = 4 and user_id = ?1", nativeQuery = true)
    int getCustomerCompleteOrder(int id);

    @Query(value = "select COUNT(*) as processing_order from shop_order\n" +
            "where order_status_id = 5 and user_id = ?1", nativeQuery = true)
    int getCustomerCancelOrder(int id);

    @Query(value = "select \n" +
            "\tid as order_id,\n" +
            "\tFORMAT(order_date, 'yyyy-MM-dd hh-mm-ss') as order_date, \n" +
            "\tFORMAT(approved_date, 'yyyy-MM-dd hh-mm-ss') as approved_date, \n" +
            "\tFORMAT(shipping_date, 'yyyy-MM-dd hh-mm-ss') as shipping_date,\n" +
            "\torder_total from shop_order where order_status_id IN (1,2,3) and user_id = ?1", nativeQuery = true)
    List<Object[]> getProcessingTableOrder(int id);

    @Query(value = "select\n" +
            "\tid as order_id, \n" +
            "\tFORMAT(order_date, 'yyyy-MM-dd hh-mm-ss') as order_date, \n" +
            "\tFORMAT(approved_date, 'yyyy-MM-dd hh-mm-ss') as approved_date, \n" +
            "\tFORMAT(shipping_date, 'yyyy-MM-dd hh-mm-ss') as shipping_date, \n" +
            "\tFORMAT(arrived_date, 'yyyy-MM-dd hh-mm-ss') as arrived_date, \n" +
            "\torder_total from shop_order where order_status_id = 4 and user_id = ?1", nativeQuery = true)
    List<Object[]> getCompleteTableOrder(int id);

    @Query(value = "select \n" +
            "\tid as order_id, \n" +
            "\tFORMAT(order_date, 'yyyy-MM-dd hh-mm-ss') as order_date, \n" +
            "\tFORMAT(approved_date, 'yyyy-MM-dd hh-mm-ss') as approved_date,\n" +
            "\tFORMAT(canceled_date, 'yyyy-MM-dd hh-mm-ss') as canceled_date, \n" +
            "\tcanceled_reason, \n" +
            "\torder_total from shop_order where order_status_id = 5 and user_id = ?1", nativeQuery = true)
    List<Object[]> getCancelTableOrder(int id);
}
