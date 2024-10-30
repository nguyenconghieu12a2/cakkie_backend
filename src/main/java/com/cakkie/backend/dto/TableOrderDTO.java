package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableOrderDTO {
    private int id;
    private String username;
    private String name;
    private String payment;
    private String status;
    private String orderTotal;


}
