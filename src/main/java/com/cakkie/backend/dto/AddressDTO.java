package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private int id;
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

    public AddressDTO(int id, int userId, String receiveName, String phone, String detailAddress, String wards, String district, String province, int isDefault, int isDelete) {
        this.id = id;
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
}
