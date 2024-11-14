package com.cakkie.backend.service;

import com.cakkie.backend.dto.OrderStatusDTO;
import com.cakkie.backend.model.orderStatus;
import com.cakkie.backend.model.shopOrder;
import com.cakkie.backend.repository.AdminOrderStatusRepo;
import com.cakkie.backend.repository.AdminShopOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminShopOrderService {
    private final AdminShopOrderRepo adminShopOrderRepo;
    private final AdminOrderStatusRepo adminOrderStatusRepo;

    public void updateOrderStatus(int shopOrderId, int newOrderStatusId) {
        // Log shopOrderId to ensure it's being received correctly
        System.out.println("Updating order status for shopOrderId: " + shopOrderId);

        // Fetch the shop order and check if it exists
        Optional<shopOrder> currentShopOrderOpt = adminShopOrderRepo.findById(shopOrderId);
        if (currentShopOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shop order not found with ID: " + shopOrderId);
        }

        shopOrder currentShopOrder = currentShopOrderOpt.get();

        // Fetch the new order status and check if it exists
        Optional<orderStatus> newOrderStatusOpt = adminOrderStatusRepo.findById(newOrderStatusId);
        if (newOrderStatusOpt.isEmpty()) {
            throw new IllegalArgumentException("Order status not found with ID: " + newOrderStatusId);
        }

        orderStatus newOrderStatus = newOrderStatusOpt.get();
        currentShopOrder.setOrderStatusId(newOrderStatus);

        // Save the updated shop order
        adminShopOrderRepo.save(currentShopOrder);
        System.out.println("Updated order status for shopOrderId: " + shopOrderId + " to new statusId: " + newOrderStatusId);
    }

    public List<OrderStatusDTO> getAllOrderStatuses() {
        return adminOrderStatusRepo.findAll().stream()
                .map(status -> new OrderStatusDTO(status.getId(), status.getStatus(), status.getIsDeleted()))
                .collect(Collectors.toList());
    }

    public OrderStatusDTO getOrderStatusById(int orderId) {
        shopOrder order = adminShopOrderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        orderStatus currentStatus = order.getOrderStatusId();
        return new OrderStatusDTO(currentStatus.getId(), currentStatus.getStatus(), currentStatus.getIsDeleted());
    }
}
