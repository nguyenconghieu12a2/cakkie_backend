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
@Table(name = "wards")
public class wards {
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

    // Prevent recursive serialization of districts within wards
    @ManyToOne
    @JoinColumn(name = "district_code", nullable = true)
    @JsonIgnore
    private districts districtCode;

    // Prevent recursive serialization of administrativeUnits within wards
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    @JsonIgnore
    private administrativeUnits administrativeUnitId;

    // Prevent recursive serialization of addresses within wards
    @OneToMany(mappedBy = "wardsCode")
    @JsonIgnore
    private List<address> addressList;
}
