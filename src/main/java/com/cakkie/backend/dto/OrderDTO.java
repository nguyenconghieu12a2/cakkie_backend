package com.cakkie.backend.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
public class OrderDTO {
    private int shopId;
    private int id;
    private String fullName;
    private int totalProduct;
    private long totalPrice;
    private long totalDiscount;
    private long price;
    private long discountPrice;
    private String status;
    private String note;
    private String proName;
    private String shipMethod;
    private Date orderDate;
    private Date approvedDate;
    private Date shippedDate;
    private Date arrivalDate;
    private String paymentMethod;
    private String address;
    private List<String> productName;

    public OrderDTO() {
    }

    public OrderDTO(int shopId, String fullName, int totalProduct, long totalPrice, long totalDiscount, String status, String note) {
        this.shopId = shopId;
        this.fullName = fullName;
        this.totalProduct = totalProduct;
        this.totalPrice  = totalPrice;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.note = note;
    }

    public OrderDTO(int id, String fullName, List<String> productName, String shipMethod, Date approvedDate, Date orderDate, Date shippedDate, Date arrivalDate, String paymentMethod, String address) {
        this.id = id;
        this.fullName = fullName;
        this.productName = productName;
        this.shipMethod = shipMethod;
        this.approvedDate = approvedDate;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.arrivalDate = arrivalDate;
        this.paymentMethod = paymentMethod;
        this.address = address;
    }
}
