package com.cakkie.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "code", length = 20, nullable = false)
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

    // Many districts belong to one province
    @ManyToOne
    @JoinColumn(name = "province_code", nullable = true)
    @JsonIgnore // Prevent recursive serialization
    private provinces provinceCode;

    // Many districts have one administrative unit
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    @JsonIgnore // Prevent recursive serialization
    private administrativeUnits administrativeUnitId;

    // One district can have multiple addresses
    @OneToMany(mappedBy = "districtsCode", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursive serialization
    private List<address> addressList;

    // One district can have multiple wards
    @OneToMany(mappedBy = "districtCode", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursive serialization
    private List<wards> districtsWardList;
}
