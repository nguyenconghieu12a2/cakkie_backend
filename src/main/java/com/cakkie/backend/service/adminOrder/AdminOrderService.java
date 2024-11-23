package com.cakkie.backend.service.adminOrder;

import com.cakkie.backend.dto.adminOrder.AdminOrderDTO;
import com.cakkie.backend.exception.adminOrder.AdminOrderNotFound;
import com.cakkie.backend.repository.adminOrder.AdminOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final AdminOrderRepository adminOrderRepository;

    public List<AdminOrderDTO> getAllOrders() {
        List<Object[]> orderData = adminOrderRepository.getAllOrders();
        List<AdminOrderDTO> adminOrderDTOList = new ArrayList<>();
        for (Object[] o : orderData) {
            int shopId = (Integer) o[0];
            String fullName = (String) o[1];
            int totalProduct = ((Number) o[2]).intValue();
            long totalPrice = ((Number) o[3]).longValue();
            long totalDiscount = ((Number) o[4]).longValue();
            String orderStatus = (String) o[5];
            String orderNote = (String) o[6];
            AdminOrderDTO adminOrderDTO = new AdminOrderDTO(shopId, fullName, totalProduct, totalPrice, totalDiscount, orderStatus, orderNote);
            adminOrderDTOList.add(adminOrderDTO);
        }
        return adminOrderDTOList;
    }

    public AdminOrderDTO getOrderById(int orderId) {
        List<Object[]> orderData = adminOrderRepository.getOrdersById(orderId);

        if (orderData.isEmpty()) {
            throw new AdminOrderNotFound("Sorry, order not found with id: " + orderId);
        }

        // Extract common data from the first row
        Object[] firstRow = orderData.get(0);
        int shopId = (Integer) firstRow[0];
        String fullName = Optional.ofNullable((String) firstRow[1]).orElse("N/A");
        String shipMethod = Optional.ofNullable((String) firstRow[6]).orElse("N/A");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderDate = Optional.ofNullable((String) firstRow[7]).orElse("N/A");
        String approvedDate = Optional.ofNullable((String) firstRow[8]).orElse("N/A");
        String shippedDate = Optional.ofNullable((String) firstRow[9]).orElse("N/A");
        String arrivalDate = Optional.ofNullable((String) firstRow[10]).orElse("N/A");
        String paymentMethod = Optional.ofNullable((String) firstRow[11]).orElse("N/A");
        String address = Optional.ofNullable((String) firstRow[12]).orElse("N/A");

        // Create empty lists to hold product names, prices, and quantities
        List<String> productNames = new ArrayList<>();
        List<Long> prices = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();
        List<String> sizes = new ArrayList<>();

        // Loop through all rows to get product details
        for (Object[] row : orderData) {
            String productName = Optional.ofNullable((String) row[2]).orElse("N/A");
            long qty = row[3] != null ? ((Number) row[3]).longValue() : 0;
            long price = row[4] != null ? ((Number) row[4]).longValue() : 0;
            String size = Optional.ofNullable((String) row[5]).orElse("N/A");

            productNames.add(productName);
            quantities.add(qty);
            prices.add(price);
            sizes.add(size);
        }

        // Create the OrderDTO object
        AdminOrderDTO order = new AdminOrderDTO(
                shopId,
                fullName,
                productNames,
                prices,
                quantities,
                shipMethod,
                parseDate(orderDate, dateFormat),
                parseDate(approvedDate, dateFormat),
                parseDate(shippedDate, dateFormat),
                parseDate(arrivalDate, dateFormat),
                paymentMethod,
                address,
                sizes
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
