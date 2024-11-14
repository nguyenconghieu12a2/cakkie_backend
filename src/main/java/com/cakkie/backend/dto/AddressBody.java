package com.cakkie.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressBody {
    private String recievedName;
    private String detailAddress;
    private String districtsCode;
    private String wardsCode;
    @JsonProperty("isDefault")
    private boolean isDefault;

    public String getRecievedName() {
        return recievedName;
    }

    public void setRecievedName(String recievedName) {
        this.recievedName = recievedName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDistrictsCode() {
        return districtsCode;
    }

    public void setDistrictsCode(String districtsCode) {
        this.districtsCode = districtsCode;
    }

    public String getWardsCode() {
        return wardsCode;
    }

    public void setWardsCode(String wardsCode) {
        this.wardsCode = wardsCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
