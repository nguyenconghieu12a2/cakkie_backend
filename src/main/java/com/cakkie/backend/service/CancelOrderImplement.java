package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;
import com.cakkie.backend.repository.CancelOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CancelOrderImplement implements CancelOrderService{

    private final CancelOrderRepository cancelOrderRepository;

    @Override
    public List<CancelOrderDTO> getAllCanceledOrder() {
        List<Object[]> cancelData = cancelOrderRepository.getAllCancelOrder();
        Map<Integer, CancelOrderDTO> cancelMap = new HashMap<>();
        for(Object[] row : cancelData) {
            Integer orderId = (Integer) row[0];
            CancelOrderDTO existCancel = cancelMap.get(orderId);
            if(existCancel == null) {
                existCancel = new CancelOrderDTO(
                   orderId,
                   (String) row[1],
                   (int) row[2]
                );
                cancelMap.put(orderId, existCancel);
            }
        }
        return new ArrayList<>(cancelMap.values());
    }


    public List<CancelOrderDTO> getDetailCancelOrderByUserId(int userId) {
        List<Object[]> cancelData = cancelOrderRepository.getDetailCancelOrderByUserId(userId);
        List<CancelOrderDTO> detailedCancelOrders = new ArrayList<>();

        for (Object[] row : cancelData) {
            CancelOrderDTO detail = new CancelOrderDTO();

            detail.setId((Integer) row[0]); // Order ID
            detail.setFullName((String) row[1]); // Customer Name
            detail.setTotalProduct(((Number) row[2]).intValue()); // Total Product
            detail.setOrderTotal(((Number) row[3]).longValue()); // Order Total
            detail.setDiscountTotal(((Number) row[4]).longValue()); // Total Discount Price
            detail.setOrderStatus((String) row[5]); // Order Status

            detailedCancelOrders.add(detail);
        }

        return detailedCancelOrders;
    }


}
