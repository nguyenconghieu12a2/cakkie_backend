package com.cakkie.backend.service;

import com.cakkie.backend.dto.CancelOrderDTO;

import java.util.List;

public class CancelOrderImplement implements CancelOrderService{

    @Override
    public List<CancelOrderDTO> getAllCanceledOrder() {
        return List.of();
    }
}
