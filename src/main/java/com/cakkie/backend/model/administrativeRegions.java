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
@Table(name = "administrative_regions")
public class administrativeRegions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "name_en", nullable = false)
    private String nameEn;
    @Column(name = "code_name", nullable = true)
    private String codeName;
    @Column(name = "code_name_en", nullable = true)
    private String codeNameEn;

    @OneToMany(mappedBy = "administrativeRegionId")
    private List<provinces> provincesList;
}
