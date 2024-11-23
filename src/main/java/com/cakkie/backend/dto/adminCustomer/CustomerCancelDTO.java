package com.cakkie.backend.dto.adminCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCancelDTO {
    private int orderId;
    private String orderDate;
    private String approvedDate;
    private String cancelDate;
    private String cancelReason;
    private String orderTotal;
}
