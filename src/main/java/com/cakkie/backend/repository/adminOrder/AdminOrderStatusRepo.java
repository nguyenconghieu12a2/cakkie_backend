package com.cakkie.backend.repository.adminOrder;

import com.cakkie.backend.model.orderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminOrderStatusRepo extends JpaRepository<orderStatus, Integer> {
    @Query("SELECT os.id, os.status, os.isDeleted FROM orderStatus os WHERE os.isDeleted = 1")
    List<Object[]> findAllOrderStatus();
}
