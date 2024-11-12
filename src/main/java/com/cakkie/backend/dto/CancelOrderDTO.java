package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private Date cancelDate;
    private String cancelReason;
    private long price;
    private long quantity;
    private List<ProductDTO> product;
    private List<ProductItemDTO> productItem;

    public CancelOrderDTO() {
    }

    public CancelOrderDTO(int id, String fullName, int totalCancel) {
        this.id = id;
        this.fullName = fullName;
        this.totalCancel = totalCancel;
    }

    public CancelOrderDTO(int id, String fullName, int totalProduct, long orderTotal, long discountTotal, Date cancelDate, String cancelReason) {
        this.id = id;
        this.fullName = fullName;
        this.totalProduct = totalProduct;
        this.orderTotal = orderTotal;
        this.discountTotal = discountTotal;
        this.cancelDate = cancelDate;
        this.cancelReason = cancelReason;
    }

    public CancelOrderDTO(int id, String fullName, long price, long quantity, List<ProductDTO> product, List<ProductItemDTO> productItem) {
        this.id = id;
        this.fullName = fullName;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productItem = productItem;
    }
}
