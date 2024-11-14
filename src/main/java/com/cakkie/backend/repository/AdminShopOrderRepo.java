package com.cakkie.backend.repository;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminShopOrderRepo extends JpaRepository<shopOrder, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE shop_order SET order_status_id = :orderStatusId WHERE id = :id", nativeQuery = true)
    void updateOrderStatus(int id, int orderStatusId);
}
