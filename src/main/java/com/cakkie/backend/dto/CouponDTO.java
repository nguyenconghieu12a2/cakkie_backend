package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponDTO {
    private int id;
    private String code;
    private String name;
    private int quantity;
    private long priceDiscount;
    private Date startDate;
    private Date endDate;
    private int isDeleted;

    public CouponDTO() {}

    public CouponDTO(int id, String code, String name, int quantity, long priceDiscount, Date startDate, Date endDate, int isDeleted) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.priceDiscount = priceDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDeleted = isDeleted;
    }
}
