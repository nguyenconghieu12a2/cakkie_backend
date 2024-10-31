package com.cakkie.backend.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
public class OrderDTO {
    private int id;
    private String fullName;
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
    private List<String> address;

    public OrderDTO() {
    }

    public OrderDTO(int id, String fullName, long price, long discountPrice, String status, String note) {
        this.id = id;
        this.fullName = fullName;
        this.price = price;
        this.discountPrice = discountPrice;
        this.status = status;
        this.note = note;
    }

    public OrderDTO(int id, String fullName, String proName, String shipMethod, Date approvedDate, Date orderDate, Date shippedDate, Date arrivalDate, String paymentMethod, List<String> address) {
        this.id = id;
        this.fullName = fullName;
        this.proName = proName;
        this.shipMethod = shipMethod;
        this.approvedDate = approvedDate;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.arrivalDate = arrivalDate;
        this.paymentMethod = paymentMethod;
        this.address = address;
    }
}
