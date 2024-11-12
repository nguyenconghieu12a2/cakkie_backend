package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO {
    private int orderStatusId;
    private String status;
    private int isDelete;

    public OrderStatusDTO() {
    }

    public OrderStatusDTO(int orderStatusId, String status, int isDelete) {
        this.orderStatusId = orderStatusId;
        this.status = status;
        this.isDelete = isDelete;
    }
}
