package com.cakkie.backend.dto.adminCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProcessingDTO {
    private int orderId;
    private String orderDate;
    private String approvedDate;
    private String shippingDate;
    private String orderTotal;
}
