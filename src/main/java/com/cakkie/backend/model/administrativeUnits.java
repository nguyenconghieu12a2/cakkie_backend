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
@Table(name = "administrative_units")
public class administrativeUnits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "full_name", nullable = true)
	private String fullName;
	@Column(name = "full_name_en", nullable = true)
	private String fullNameEn;
	@Column(name = "short_name", nullable = true)
	private String shortName;
	@Column(name = "short_name_en", nullable = true)
	private String shortNameEn;
	@Column(name = "code_name", nullable = true)
	private String codeName;
	@Column(name = "code_name_en", nullable = true)
	private String codeNameEn;

	@OneToMany(mappedBy = "administrativeUnitId")
	private List<provinces> provincesList;

	@OneToMany(mappedBy = "administrativeUnitId")
	private List<districts> districtsList;

	@OneToMany(mappedBy = "administrativeUnitId")
	private List<wards> wardsList;
}
