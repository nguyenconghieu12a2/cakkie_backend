package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;
import com.cakkie.backend.model.userSite;

import java.util.List;
import java.util.Optional;

public interface CancelOrderService {
    List<CancelOrderDTO> getAllCanceledOrder();
    List<CancelOrderDTO> getDetailCancelOrderByUserId(int userId);
    List<CancelOrderDTO> getDetailProductCancelByUserId(int userId);
    void banUser(int userId, String bannedReason);
    Optional<userSite> findUserById(int userId);
}
