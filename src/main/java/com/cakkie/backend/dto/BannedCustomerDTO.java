package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannedCustomerDTO {
    private int userId;
    private String bannedReason;

    public BannedCustomerDTO() {
    }

    public BannedCustomerDTO(int userId, String bannedReason) {
        this.userId = userId;
        this.bannedReason = bannedReason;
    }
}
