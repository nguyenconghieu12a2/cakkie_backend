package com.cakkie.backend.service;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.exception.OrderNotFound;
import com.cakkie.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDTO> getAllOrders() {
        List<Object[]> orderData = orderRepository.getAllOrders();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Object[] o : orderData) {
            int shopId = (Integer) o[0];
            String fullName = (String) o[1];
            int totalProduct = ((Number) o[2]).intValue();
            long totalPrice = ((Number) o[3]).longValue();
            long totalDiscount = ((Number) o[4]).longValue();
            String orderStatus = (String) o[5];
            String orderNote = (String) o[6];
            OrderDTO orderDTO = new OrderDTO(shopId, fullName, totalProduct, totalPrice, totalDiscount, orderStatus, orderNote);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    public OrderDTO getOrderById(int orderId) {
        List<Object[]> orderData = orderRepository.getOrdersById(orderId);

        if(orderData.isEmpty()) {
            throw new OrderNotFound("Sorry, order not found with id: " + orderId);
        }

        Object[] o = orderData.get(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date orderDate = parseDate((String) o[4], dateFormat);
        Date approvedDate = parseDate((String) o[5], dateFormat);
        Date shippedDate = parseDate((String) o[6], dateFormat);
        Date arrivalDate = parseDate((String) o[7], dateFormat);

        OrderDTO order = new OrderDTO(
                (Integer) o[0],
                (String) o[1],
                new ArrayList<>(),
                (String) o[3],
                orderDate,
                approvedDate,
                shippedDate,
                arrivalDate,
                (String) o[8],
                (String) o[9]
        );
        for(Object[] rows: orderData) {
            String productName = (String) rows[2];
//            String address = (String) rows[9];
//            order.getAddress().add(address);
            order.getProductName().add(productName);
        }
        return order;
    }

    private Date parseDate(String dateStr, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
