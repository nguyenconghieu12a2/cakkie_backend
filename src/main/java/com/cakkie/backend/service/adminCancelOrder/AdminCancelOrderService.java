package com.cakkie.backend.service.adminCancelOrder;

import com.cakkie.backend.dto.adminCaceledOrder.AdminCancelOrderDTO;
import com.cakkie.backend.model.userSite;

import java.util.List;
import java.util.Optional;

public interface AdminCancelOrderService {
    List<AdminCancelOrderDTO> getAllCanceledOrder();
    List<AdminCancelOrderDTO> getDetailCancelOrderByUserId(int userId);
    List<AdminCancelOrderDTO> getDetailProductCancelByUserId(int userId);
    void banUser(int userId, String bannedReason);
    Optional<userSite> findUserById(int userId);
    boolean isUserBanned(int userId);
}
