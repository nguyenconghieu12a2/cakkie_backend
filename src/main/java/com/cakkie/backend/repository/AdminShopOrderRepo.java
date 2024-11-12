package com.cakkie.backend.repository;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminShopOrderRepo extends JpaRepository<shopOrder, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE shop_order SET order_status_id = ?2 WHERE id = ?1", nativeQuery = true)
    void updateOrderStatus(int orderStatusId, int id);

    @Query(value = "SELECT o.status FROM shop_order s JOIN order_status o ON s.order_status_id = o.id WHERE s.id = ?1", nativeQuery = true)
    String findOrderStatusNameById(int id);
}
