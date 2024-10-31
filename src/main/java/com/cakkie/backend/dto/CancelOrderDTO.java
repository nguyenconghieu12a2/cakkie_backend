package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderDTO {
    private int id;
    private String fullName;
    private int totalCancel;

    public CancelOrderDTO() {
    }

    public CancelOrderDTO(int id, String fullName, int totalCancel) {
        this.id = id;
        this.fullName = fullName;
        this.totalCancel = totalCancel;
    }
}
