package com.cakkie.backend.dto.adminBannedCustomer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBannedCustomerDTO {
    private int userId;
    private String bannedReason;

    public AdminBannedCustomerDTO() {
    }

    public AdminBannedCustomerDTO(int userId, String bannedReason) {
        this.userId = userId;
        this.bannedReason = bannedReason;
    }
}
