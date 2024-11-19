package com.cakkie.backend.dto.adminCaceledOrder;

import com.cakkie.backend.dto.adminProduct.AdminProductDTO;
import com.cakkie.backend.dto.adminProduct.AdminProductItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AdminCancelOrderDTO {
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
    private List<AdminProductDTO> product;
    private List<AdminProductItemDTO> productItem;

    public AdminCancelOrderDTO() {
    }

    public AdminCancelOrderDTO(int id, String fullName, int totalCancel) {
        this.id = id;
        this.fullName = fullName;
        this.totalCancel = totalCancel;
    }

    public AdminCancelOrderDTO(int id, String fullName, int totalProduct, long orderTotal, long discountTotal, Date cancelDate, String cancelReason) {
        this.id = id;
        this.fullName = fullName;
        this.totalProduct = totalProduct;
        this.orderTotal = orderTotal;
        this.discountTotal = discountTotal;
        this.cancelDate = cancelDate;
        this.cancelReason = cancelReason;
    }

    public AdminCancelOrderDTO(int id, String fullName, long price, long quantity, List<AdminProductDTO> product, List<AdminProductItemDTO> productItem) {
        this.id = id;
        this.fullName = fullName;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productItem = productItem;
    }
}
