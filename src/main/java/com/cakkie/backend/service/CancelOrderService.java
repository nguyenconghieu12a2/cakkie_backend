package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;
import java.util.List;

public interface CancelOrderService {
    List<CancelOrderDTO> getAllCanceledOrder();
}
