package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableCustomerDTO {
    private int id;
    private String name;
    private String email;
    private int totalOrder;
    private String totalPayment;
}
