package com.cakkie.backend.service;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.exception.OrderNotFound;
import com.cakkie.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderImplement implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public OrderDTO getOrdersById(int id) {
        List<Object[]> orderData = orderRepository.getOrdersById(id);

        if(orderData.isEmpty()) {
            throw new OrderNotFound("Sorry, order not found with id: " + id);
        }

        Object[] row = orderData.get(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date orderDate = parseDate((String) row[4], dateFormat);
        Date approvedDate = parseDate((String) row[5], dateFormat);
        Date shippedDate = parseDate((String) row[6], dateFormat);
        Date arrivalDate = parseDate((String) row[7], dateFormat);

        OrderDTO order = new OrderDTO(
        (Integer) row[0], //id
        (String) row[1] ,//fullName
        (String) row[2], //productName
        (String) row[3], //shipName
        orderDate,
        approvedDate,// approvedDate
        shippedDate,// shippedDate
        arrivalDate,// arrivalDate
        (String) row[8], //payMethod
        new ArrayList<>()//address
        );

        for(Object[] rows : orderData ) {
            String address = (String) rows[9];
            order.getAddress().add(address);
        }
        return order;
    }

    private Date parseDate(String dateStr, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Date parsing failed for date: " + dateStr, e);
        }
    }
}
