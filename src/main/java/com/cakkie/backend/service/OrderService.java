package com.cakkie.backend.service;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.shopOrder;

import java.util.List;

public interface OrderService {
    shopOrder createOrder(OrderDTO order);
    shopOrder getOrderById(Integer orderId);
    void updateOrder(shopOrder order);
}
