package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int id;
    private int orderId;
    private int productItemId;
    private int quantity;
    private long price;
    private long discountPrice;
    private String note;
    private int reviewId;
    private int orderLineId;

}
