package com.cakkie.backend.service;
import com.cakkie.backend.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrdersById(int id);
}
