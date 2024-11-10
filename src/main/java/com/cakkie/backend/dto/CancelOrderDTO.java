package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderDTO {
    private int id;
    private String fullName;
    private int totalCancel;
    private int totalProduct;
    private long orderTotal;
    private long discountTotal;
    private String orderStatus;

    public CancelOrderDTO() {
    }

    public CancelOrderDTO(int id, String fullName, int totalCancel) {
        this.id = id;
        this.fullName = fullName;
        this.totalCancel = totalCancel;
    }

    public CancelOrderDTO(int id, String fullName, int totalProduct, long orderTotal, long discountTotal, String orderStatus) {
        this.id = id;
        this.fullName = fullName;
        this.totalProduct = totalProduct;
        this.orderTotal = orderTotal;
        this.discountTotal = discountTotal;
        this.orderStatus = orderStatus;
    }
}
