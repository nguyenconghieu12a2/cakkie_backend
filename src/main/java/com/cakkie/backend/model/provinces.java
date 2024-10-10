package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "provinces")
public class provinces {
    @Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "name_en", nullable = true)
    private String nameEn;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "full_name_en", nullable = true)
    private String fullNameEn;
    @Column(name = "code_name", nullable = true)
    private String codeName;
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    private administrativeUnits administrativeUnitId;
    @ManyToOne
    @JoinColumn(name = "administrative_region_id", nullable = true)
    private administrativeRegions administrativeRegionId;

    @OneToMany(mappedBy = "provinceCode")
    private List<districts> districtsCode;
}
