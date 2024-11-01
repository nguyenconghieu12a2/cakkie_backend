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
@Table(name = "address")
public class address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "recieved_name", nullable = false)
	private String recievedName;
	@Column(name = "detail_address", nullable = false)
	private String detailAddress;
	@ManyToOne
	@JoinColumn(name = "districts_code", nullable = false)
	private districts districtsCode;
	@ManyToOne
	@JoinColumn(name = "wards_code", nullable = false)
	private wards wardsCode;
	@Column(name = "is_deleted", nullable = false)
	private int isDeleted;

	@JsonIgnore
	@OneToMany(mappedBy = "addressId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<userAddress> userAddresses;

	@JsonIgnore
	@OneToMany(mappedBy = "shippingAddressId")
	private List<shopOrder> shopOrdersList;

}
