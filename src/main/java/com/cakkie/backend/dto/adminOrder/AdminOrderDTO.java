package com.cakkie.backend.dto.adminOrder;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Getter
public class AdminOrderDTO {
    private int shopId;
    private int id;
    private String fullName;
    private int totalProduct;
    private long totalPrice;
    private long totalDiscount;
    private List<Long> price;
    private List<Long> qty;
    private List<String> size;
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

    public AdminOrderDTO() {
    }

    public AdminOrderDTO(int shopId, String fullName, int totalProduct, long totalPrice, long totalDiscount, String status, String note) {
        this.shopId = shopId;
        this.fullName = fullName;
        this.totalProduct = totalProduct;
        this.totalPrice  = totalPrice;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.note = note;
    }

    public AdminOrderDTO(int id, String fullName, List<String> productName, List<Long> price, List<Long> qty, String shipMethod, Date approvedDate, Date orderDate, Date shippedDate, Date arrivalDate, String paymentMethod, String address, List<String> size) {
        this.id = id;
        this.fullName = fullName;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.shipMethod = shipMethod;
        this.approvedDate = approvedDate;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.arrivalDate = arrivalDate;
        this.paymentMethod = paymentMethod;
        this.address = address;
        this.size = size;
    }
}
