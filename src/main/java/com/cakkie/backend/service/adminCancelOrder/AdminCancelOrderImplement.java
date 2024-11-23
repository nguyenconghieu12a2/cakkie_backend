package com.cakkie.backend.service.adminCancelOrder;

import com.cakkie.backend.dto.adminCaceledOrder.AdminCancelOrderDTO;
import com.cakkie.backend.dto.adminProduct.AdminProductDTO;
import com.cakkie.backend.dto.adminProduct.AdminProductItemDTO;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.repository.adminCancelOrder.AdminCancelOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminCancelOrderImplement implements AdminCancelOrderService {

    private final AdminCancelOrderRepository adminCancelOrderRepository;

    @Override
    public List<AdminCancelOrderDTO> getAllCanceledOrder() {
        List<Object[]> cancelData = adminCancelOrderRepository.getAllCancelOrder();
        Map<Integer, AdminCancelOrderDTO> cancelMap = new HashMap<>();
        for(Object[] row : cancelData) {
            Integer orderId = (Integer) row[0];
            AdminCancelOrderDTO existCancel = cancelMap.get(orderId);
            if(existCancel == null) {
                existCancel = new AdminCancelOrderDTO(
                   orderId,
                   (String) row[1],
                   (int) row[2]
                );
                cancelMap.put(orderId, existCancel);
            }
        }
        return new ArrayList<>(cancelMap.values());
    }


    public List<AdminCancelOrderDTO> getDetailCancelOrderByUserId(int userId) {
        List<Object[]> cancelData = adminCancelOrderRepository.getDetailCancelOrderByUserId(userId);
        List<AdminCancelOrderDTO> detailedCancelOrders = new ArrayList<>();

        for (Object[] row : cancelData) {
            AdminCancelOrderDTO detail = new AdminCancelOrderDTO();

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
    public List<AdminCancelOrderDTO> getDetailProductCancelByUserId(int userId) {
        List<Object[]> productCancelData = adminCancelOrderRepository.getAllProductCancelByUserId(userId);
        Map<Integer, AdminCancelOrderDTO> orderMap = new LinkedHashMap<>();

        for (Object[] row : productCancelData) {
            Integer orderId = (Integer) row[0];
            String customerName = (String) row[1];
            String productName = (String) row[2];
            String productSize = (String) row[3];
            Integer quantity = ((Number) row[4]).intValue();
            Long price = ((Number) row[5]).longValue();

            // Get or create the CancelOrderDTO for this order ID
            AdminCancelOrderDTO adminCancelOrderDTO = orderMap.computeIfAbsent(orderId, id -> {
                AdminCancelOrderDTO dto = new AdminCancelOrderDTO();
                dto.setId(orderId);
                dto.setFullName(customerName);
                dto.setProduct(new ArrayList<>()); // Initialize product list
                return dto;
            });

            // Create ProductDTO and ProductItemDTO
            AdminProductDTO adminProductDTO = new AdminProductDTO();
            adminProductDTO.setProductName(productName);

            AdminProductItemDTO adminProductItemDTO = new AdminProductItemDTO();
            adminProductItemDTO.setSize(productSize);
            adminProductItemDTO.setQuantity(quantity);
            adminProductItemDTO.setPrice(price);

            // Add ProductItemDTO to the product's item list and add product to CancelOrderDTO
            adminProductDTO.setProductItem(List.of(adminProductItemDTO));
            adminCancelOrderDTO.getProduct().add(adminProductDTO);
        }

        return new ArrayList<>(orderMap.values());
    }

    @Transactional
    @Override
    public void banUser(int userId, String bannedReason) {
        Optional<userSite> userOptional = adminCancelOrderRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        userSite user = userOptional.get();
        if (user.getStatusId().getId() == 2) {
            throw new IllegalArgumentException("User is already banned. Reason: " + user.getBannedReason());
        }

        adminCancelOrderRepository.banUser(userId, bannedReason);
    }

    public boolean isUserBanned(int userId) {
        Optional<userSite> userOptional = adminCancelOrderRepository.findUserById(userId);
        return userOptional.isPresent() && userOptional.get().getStatusId().equals(2);
    }

    @Override
    public Optional<userSite> findUserById(int userId) {
        return adminCancelOrderRepository.findUserById(userId);
    }

}
