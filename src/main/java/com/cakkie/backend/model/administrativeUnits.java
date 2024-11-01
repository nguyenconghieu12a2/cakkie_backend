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
	@JsonIgnore // Prevent recursive serialization of provinces within administrativeUnits
	private List<provinces> provincesList;

	@OneToMany(mappedBy = "administrativeUnitId")
	@JsonIgnore // Prevent recursive serialization of districts within administrativeUnits
	private List<districts> districtsList;

	@OneToMany(mappedBy = "administrativeUnitId")
	@JsonIgnore // Prevent recursive serialization of wards within administrativeUnits
	private List<wards> wardsList;
}
