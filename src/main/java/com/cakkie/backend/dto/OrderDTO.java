package com.cakkie.backend.dto;

import com.cakkie.backend.model.orderLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private int userId;
    private String shippingMethod;
    private int shippingAddress;
    private String paymentMethod;
    private int orderStatus;
    private int couponsId;
    private Date orderDate;
    private Date arrivedDate;
    private Date canceledDate;
    private List<OrderItemDTO> orderLineList;


    public OrderDTO(int orderId, int userId, String shippingMethod, int shippingAddress, String paymentMethod, int orderStatus, Date orderDate, Date arrivedDate, Date canceledDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.arrivedDate = arrivedDate;
        this.canceledDate = canceledDate;
    }

}
