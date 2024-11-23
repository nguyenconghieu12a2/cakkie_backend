package com.cakkie.backend.dto.adminCoupon;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class AdminCouponDTO {
    private int id;
    private String code;
    private String name;
    private int quantity;
    private long priceDiscount;
    private Date startDate;
    private Date endDate;
    private String startDateFormatted; // New field for formatted start date
    private String endDateFormatted;  // New field for formatted end date
    private int isDeleted;

    public AdminCouponDTO() {}

    public AdminCouponDTO(int id, String code, String name, int quantity, long priceDiscount, Date startDate, Date endDate, int isDeleted) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.priceDiscount = priceDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDeleted = isDeleted;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startDateFormatted = formatter.format(startDate);
        this.endDateFormatted = formatter.format(endDate);
    }
}


