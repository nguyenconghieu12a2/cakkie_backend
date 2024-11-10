package com.cakkie.backend.dto.adminCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String gender;
    private String birthday;
    private String email;
    private String phone;
    private String accountCreateDate;
    private List<String> customerAddress;
    private String status;
}
