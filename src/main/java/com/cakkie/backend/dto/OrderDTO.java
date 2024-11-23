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
    private int shippingMethodId;
    private int shippingAddress;
    private String paymentMethod;
    private int paymentMethodId;
    private int orderStatus;
    private int couponsId;
    private Date orderDate;
    private Date arrivedDate;
    private Date canceledDate;
    private long orderTotal;
    private List<OrderItemDTO> orderLineList;
    private Date shippingDate;

    public OrderDTO(int orderId, int userId, String shippingMethod, int shippingAddress, String paymentMethod, int orderStatus, Date orderDate, Date arrivedDate, Date canceledDate, Date shippingDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.arrivedDate = arrivedDate;
        this.canceledDate = canceledDate;
        this.shippingDate = shippingDate;
    }
    public OrderDTO(int orderId, int userId, String shippingMethod, int shippingMethodId, int shippingAddress, String paymentMethod, int paymentMethodId, int orderStatus, int couponsId, Date orderDate, Date arrivedDate, Date canceledDate, long orderTotal, List<OrderItemDTO> orderLineList, Date shippingDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingMethod = shippingMethod;
        this.shippingMethodId = shippingMethodId;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.paymentMethodId = paymentMethodId;
        this.orderStatus = orderStatus;
        this.couponsId = couponsId;
        this.orderDate = orderDate;
        this.arrivedDate = arrivedDate;
        this.canceledDate = canceledDate;
        this.orderTotal = orderTotal;
        this.orderLineList = orderLineList;
        this.shippingDate = shippingDate;
    }

    public OrderDTO(int orderId, int userId, String shippingMethod, int shippingMethodId, int shippingAddress, String paymentMethod, int paymentMethodId, int orderStatus, int couponsId, Date orderDate, Date arrivedDate, Date canceledDate, long orderTotal) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingMethod = shippingMethod;
        this.shippingMethodId = shippingMethodId;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.paymentMethodId = paymentMethodId;
        this.orderStatus = orderStatus;
        this.couponsId = couponsId;
        this.orderDate = orderDate;
        this.arrivedDate = arrivedDate;
        this.canceledDate = canceledDate;
        this.orderTotal = orderTotal;
    }

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
