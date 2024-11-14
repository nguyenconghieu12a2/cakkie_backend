package com.cakkie.backend.dto;

public class AddressResponse {
    private int addressId;
    private String receivedName;
    private String detailAddress;
    private String provinceCode;
    private String provinceName;
    private String districtName;
    private String wardName;
    private String phone;
    private boolean isDefault;

    public AddressResponse(int addressId, String receivedName, String detailAddress, String provinceCode, String provinceName, String districtName, String wardName, String phone, boolean isDefault) {
        this.addressId = addressId;
        this.receivedName = receivedName;
        this.detailAddress = detailAddress;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.districtName = districtName;
        this.wardName = wardName;
        this.phone = phone;
        this.isDefault = isDefault;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getReceivedName() {
        return receivedName;
    }

    public void setReceivedName(String receivedName) {
        this.receivedName = receivedName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
