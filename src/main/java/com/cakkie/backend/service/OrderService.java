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

        if (orderData.isEmpty()) {
            throw new OrderNotFound("Sorry, order not found with id: " + orderId);
        }

        // Extract common data from the first row
        Object[] firstRow = orderData.get(0);
        int shopId = (Integer) firstRow[0];
        String fullName = (String) firstRow[1];
        String shipMethod = (String) firstRow[5];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date orderDate = parseDate((String) firstRow[6], dateFormat);
        Date approvedDate = parseDate((String) firstRow[7], dateFormat);
        Date shippedDate = parseDate((String) firstRow[8], dateFormat);
        Date arrivalDate = parseDate((String) firstRow[9], dateFormat);
        String paymentMethod = (String) firstRow[10];
        String address = (String) firstRow[11];

        // Create empty lists to hold product names, prices, and quantities
        List<String> productNames = new ArrayList<>();
        List<Long> prices = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();

        // Loop through all rows to get product details
        for (Object[] row : orderData) {
            String productName = (String) row[2];
            long qty = ((Number) row[3]).longValue();
            long price = ((Number) row[4]).longValue();

            productNames.add(productName);
            quantities.add(qty);
            prices.add(price);
        }

        // Create the OrderDTO object
        OrderDTO order = new OrderDTO(
                shopId,
                fullName,
                productNames,
                prices,
                quantities,
                shipMethod,
                approvedDate,
                orderDate,
                shippedDate,
                arrivalDate,
                paymentMethod,
                address
        );

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
