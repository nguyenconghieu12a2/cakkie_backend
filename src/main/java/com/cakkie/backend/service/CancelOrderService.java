package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;
import java.util.List;

public interface CancelOrderService {
    List<CancelOrderDTO> getAllCanceledOrder();
    List<CancelOrderDTO> getDetailCancelOrderByUserId(int userId);
    List<CancelOrderDTO> getDetailProductCancelByUserId(int userId);
}
