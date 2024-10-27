package com.cakkie.backend.dto;

import java.util.Date;

public class OrderDTO {
    private int orderId;
    private int userId;
    private String shippingMethod;
    private int shippingAddress;
    private String paymentMethod;
    private String orderStatus;
    private Date orderDate;
    private Date arrivedDate;
    private Date canceledDate;

    public OrderDTO(int orderId, int userId, String shippingMethod, int shippingAddress, String paymentMethod, String orderStatus, Date orderDate, Date arrivedDate, Date canceledDate) {
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public int getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(int shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public Date getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(Date canceledDate) {
        this.canceledDate = canceledDate;
    }
}
