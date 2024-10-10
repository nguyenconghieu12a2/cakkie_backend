package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "districts")
public class districts {
    @Id
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "name_en", nullable = true)
    private String nameEn;
    @Column(name = "full_name", nullable = true)
    private String fullName;
    @Column(name = "full_name_en", nullable = true)
    private String fullNameEn;
    @Column(name = "code_name", nullable = true)
    private String codeName;
    @ManyToOne
    @JoinColumn(name = "province_code", nullable = true)
    private provinces provinceCode;
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    private administrativeUnits administrativeUnitId;

    @OneToMany(mappedBy = "districtsCode")
    private List<address> addressList;
    @OneToMany(mappedBy = "districtCode")
    private List<wards> districtsWardList;
}
