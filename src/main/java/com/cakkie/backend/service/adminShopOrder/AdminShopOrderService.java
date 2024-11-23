package com.cakkie.backend.service.adminShopOrder;

import com.cakkie.backend.dto.adminOrder.AdminOrderStatusDTO;
import com.cakkie.backend.model.orderStatus;
import com.cakkie.backend.model.shopOrder;
import com.cakkie.backend.repository.adminOrder.AdminOrderStatusRepo;
import com.cakkie.backend.repository.adminShopOrder.AdminShopOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminShopOrderService {
    private final AdminShopOrderRepo adminShopOrderRepo;
    private final AdminOrderStatusRepo adminOrderStatusRepo;

//    public void updateOrderStatus(int shopOrderId, int newOrderStatusId) {
//        System.out.println("Updating order status for shopOrderId: " + shopOrderId);
//
//        Optional<shopOrder> currentShopOrderOpt = adminShopOrderRepo.findById(shopOrderId);
//        if (currentShopOrderOpt.isEmpty()) {
//            throw new IllegalArgumentException("Shop order not found with ID: " + shopOrderId);
//        }
//
//        shopOrder currentShopOrder = currentShopOrderOpt.get();
//
//        Optional<orderStatus> newOrderStatusOpt = adminOrderStatusRepo.findById(newOrderStatusId);
//        if (newOrderStatusOpt.isEmpty()) {
//            throw new IllegalArgumentException("Order status not found with ID: " + newOrderStatusId);
//        }
//
//        orderStatus newOrderStatus = newOrderStatusOpt.get();
//        String currentStatus = currentShopOrder.getOrderStatusId().getStatus();
//        String newStatus = newOrderStatus.getStatus();
//
//        if (!isValidStatusTransition(currentStatus, newStatus)) {
//            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
//        }
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        if (newStatus.equals("Confirm") && !currentStatus.equals("Confirm")) {
//            currentShopOrder.setApprovedDate(new Date());
//        } else if (newStatus.equals("Shipping") && !currentStatus.equals("Shipping")) {
//            currentShopOrder.setShippingDate(new Date());
//        } else if (newStatus.equals("Arrived") && !currentStatus.equals("Arrived")) {
//            currentShopOrder.setArrivedDate(new Date());
//        } else if (newStatus.equals("Cancel") && !currentStatus.equals("Cancel")) {
//            currentShopOrder.setCanceledDate(new Date());
//        }
//
//        currentShopOrder.setOrderStatusId(newOrderStatus);
//
//        adminShopOrderRepo.save(currentShopOrder);
//        System.out.println("Updated order status for shopOrderId: " + shopOrderId + " to new statusId: " + newOrderStatusId);
//    }

    public void updateOrderStatus(int shopOrderId, int newOrderStatusId, String cancelReason) {
        System.out.println("Updating order status for shopOrderId: " + shopOrderId);

        Optional<shopOrder> currentShopOrderOpt = adminShopOrderRepo.findById(shopOrderId);
        if (currentShopOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shop order not found with ID: " + shopOrderId);
        }

        shopOrder currentShopOrder = currentShopOrderOpt.get();

        Optional<orderStatus> newOrderStatusOpt = adminOrderStatusRepo.findById(newOrderStatusId);
        if (newOrderStatusOpt.isEmpty()) {
            throw new IllegalArgumentException("Order status not found with ID: " + newOrderStatusId);
        }

        orderStatus newOrderStatus = newOrderStatusOpt.get();
        String currentStatus = currentShopOrder.getOrderStatusId().getStatus();
        String newStatus = newOrderStatus.getStatus();

        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        if(newStatus.equals("Cancel")) {
            if(cancelReason == null || cancelReason.isEmpty()) {
                throw new IllegalArgumentException("Cancel reason cannot be null or empty");
            }
            currentShopOrder.setCanceledDate(new Date());
            currentShopOrder.setCanceledReason(cancelReason);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (newStatus.equals("Confirm") && !currentStatus.equals("Confirm")) {
            currentShopOrder.setApprovedDate(new Date());
        } else if (newStatus.equals("Shipping") && !currentStatus.equals("Shipping")) {
            currentShopOrder.setShippingDate(new Date());
        } else if (newStatus.equals("Arrived") && !currentStatus.equals("Arrived")) {
            currentShopOrder.setArrivedDate(new Date());
        }

        currentShopOrder.setOrderStatusId(newOrderStatus);

        adminShopOrderRepo.save(currentShopOrder);
        System.out.println("Updated order status for shopOrderId: " + shopOrderId + " to new statusId: " + newOrderStatusId);
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus.equals("Arrived") && newStatus.equals("Cancel")) {
            return false;
        }

        if (newStatus.equals("Cancel")) {
            return true;
        }

        switch (currentStatus) {
            case "Ordered":
                return newStatus.equals("Confirm");
            case "Confirm":
                return newStatus.equals("Shipping");
            case "Shipping":
                return newStatus.equals("Arrived");
            default:
                return false;
        }
    }

    public List<AdminOrderStatusDTO> getAllOrderStatuses() {
        return adminOrderStatusRepo.findAll().stream()
                .map(status -> new AdminOrderStatusDTO(status.getId(), status.getStatus(), status.getIsDeleted()))
                .collect(Collectors.toList());
    }

    public AdminOrderStatusDTO getOrderStatusById(int orderId) {
        shopOrder order = adminShopOrderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        orderStatus currentStatus = order.getOrderStatusId();
        return new AdminOrderStatusDTO(currentStatus.getId(), currentStatus.getStatus(), currentStatus.getIsDeleted());
    }
}
