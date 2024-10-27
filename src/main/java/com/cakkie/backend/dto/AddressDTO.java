package com.cakkie.backend.dto;

public class AddressDTO {
    private int userId;
    private String receiveName;
    private String phone;
    private String detailAddress;
    private String wards;
    private String district;
    private String province;
    private int isDefault;
    private int isDelete;

    public AddressDTO() {
    }

    public AddressDTO(int userId, String receiveName, String detailAddress, String wards, String district, String province, int isDefault, int isDelete) {
        this.userId = userId;
        this.receiveName = receiveName;
        this.detailAddress = detailAddress;
        this.wards = wards;
        this.district = district;
        this.province = province;
        this.isDefault = isDefault;
        this.isDelete = isDelete;
    }

    public AddressDTO(int id, String receiveName, String detailAddress, String wards, String district, String province, int isDefault) {
        this.userId = id;
        this.receiveName = receiveName;
        this.detailAddress = detailAddress;
        this.wards = wards;
        this.district = district;
        this.province = province;
        this.isDefault = isDefault;
    }

    public AddressDTO(int userId, String receiveName, String phone, String detailAddress, String wards, String district, String province, int isDefault, int isDelete) {
        this.userId = userId;
        this.receiveName = receiveName;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.wards = wards;
        this.district = district;
        this.province = province;
        this.isDefault = isDefault;
        this.isDelete = isDelete;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getWards() {
        return wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int  isDelete) {
        this.isDelete = isDelete;
    }
}
