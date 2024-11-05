package com.cakkie.backend.implement;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.*;
import com.cakkie.backend.repository.*;
import com.cakkie.backend.service.CartService;
import com.cakkie.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderImplement implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserPaymentMethodRepository userPaymentMethodRepository;

    @Autowired
    private ShippingMethodRepository shippingMethodRepository;
    public shopOrder createOrder(OrderDTO orderDTO) {
        orderStatus status = orderStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + 1));
        userSite user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + 1));
        address address = addressRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + 1));
        userPaymentMethod userPaymentMethod = userPaymentMethodRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + 1));
        shippingMethod shippingMethod = shippingMethodRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + 1));
        shopOrder order = new shopOrder();
        // ... set other fields from orderDto, including order_status_id = 1
        order.setUserId(user);

        order.setShippingMethodId(shippingMethod);
        order.setShippingAddressId(address);
        order.setPaymentMethod(userPaymentMethod);
        order.setOrderStatusId(status);
        order.setOrderTotal(1000);
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        order.setOrderDate(date);
        // Save the order first to get the generated ID
        order = orderRepository.save(order);

        // Create and save order lines
//        shopOrder finalOrder = order;
//        List<orderLine> orderLines = orderDTO.getProductItemIds().stream()
//                .map(productId -> {
//                    orderLine orderLine = new orderLine();
//                    orderLine.setOrderId(finalOrder);
//                    orderLine.setProductItemId(productId);
//                    // ... set other fields for order line
//                    return orderLine;
//                })
//                .collect(Collectors.toList());
//        orderLineRepository.saveAll(orderLines);

        return order;
    }
}
