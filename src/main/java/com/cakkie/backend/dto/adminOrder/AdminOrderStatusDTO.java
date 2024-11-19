package com.cakkie.backend.dto.adminOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOrderStatusDTO {
    private int orderStatusId;
    private String status;
    private int isDelete;

    public AdminOrderStatusDTO() {
    }

    public AdminOrderStatusDTO(int orderStatusId, String status, int isDelete) {
        this.orderStatusId = orderStatusId;
        this.status = status;
        this.isDelete = isDelete;
    }
}
