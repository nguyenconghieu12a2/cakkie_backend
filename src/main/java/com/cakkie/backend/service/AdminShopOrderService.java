package com.cakkie.backend.service;

import com.cakkie.backend.dto.OrderStatusDTO;
import com.cakkie.backend.model.orderStatus;
import com.cakkie.backend.repository.AdminOrderStatusRepo;
import com.cakkie.backend.repository.AdminShopOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminShopOrderService {
    private final AdminShopOrderRepo adminShopOrderRepo;
    private final AdminOrderStatusRepo adminOrderStatusRepo;

    public void updateOrderStatus(int id, int orderStatusId) {
        adminShopOrderRepo.updateOrderStatus(id, orderStatusId);
    }

    public List<OrderStatusDTO> getAllOrderStatus() {
        List<Object[]> status = adminOrderStatusRepo.findAllOrderStatus();
        Map<Integer, OrderStatusDTO> map = new HashMap<>();

        for (Object[] o : status) {
            int id = (Integer) o[0];
            String orderStatus = (String) o[1];

            OrderStatusDTO statusDTO = new OrderStatusDTO(id, orderStatus, 1);
            map.put(id, statusDTO);
        }
        return new ArrayList<>(map.values());
    }

    public String getCurrentOrderStatusName(int orderId) {
        return adminShopOrderRepo.findOrderStatusNameById(orderId);
    }

}
