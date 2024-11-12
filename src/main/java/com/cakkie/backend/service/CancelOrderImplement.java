package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;
import com.cakkie.backend.dto.ProductDTO;
import com.cakkie.backend.dto.ProductItemDTO;
import com.cakkie.backend.repository.CancelOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
            detail.setCancelDate((Date) row[5]); // Canceled Date
            detail.setCancelReason((String) row[6]); // Canceled Reason

            detailedCancelOrders.add(detail);
        }

        return detailedCancelOrders;
    }

    @Override
    public List<CancelOrderDTO> getDetailProductCancelByUserId(int userId) {
        List<Object[]> productCancelData = cancelOrderRepository.getAllProductCancelByUserId(userId);
        Map<Integer, CancelOrderDTO> orderMap = new LinkedHashMap<>();

        for (Object[] row : productCancelData) {
            Integer orderId = (Integer) row[0];
            String customerName = (String) row[1];
            String productName = (String) row[2];
            String productSize = (String) row[3];
            Integer quantity = ((Number) row[4]).intValue();
            Long price = ((Number) row[5]).longValue();

            // Get or create the CancelOrderDTO for this order ID
            CancelOrderDTO cancelOrderDTO = orderMap.computeIfAbsent(orderId, id -> {
                CancelOrderDTO dto = new CancelOrderDTO();
                dto.setId(orderId);
                dto.setFullName(customerName);
                dto.setProduct(new ArrayList<>()); // Initialize product list
                return dto;
            });

            // Create ProductDTO and ProductItemDTO
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductName(productName);

            ProductItemDTO productItemDTO = new ProductItemDTO();
            productItemDTO.setSize(productSize);
            productItemDTO.setQuantity(quantity);
            productItemDTO.setPrice(price);

            // Add ProductItemDTO to the product's item list and add product to CancelOrderDTO
            productDTO.setProductItem(List.of(productItemDTO));
            cancelOrderDTO.getProduct().add(productDTO);
        }

        return new ArrayList<>(orderMap.values());
    }


}
