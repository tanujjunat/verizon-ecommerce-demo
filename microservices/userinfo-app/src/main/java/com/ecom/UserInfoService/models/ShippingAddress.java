package com.ecom.UserInfoService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {

	@Id
	private Long id;
	private Long userId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private int postalCode;

	public ShippingAddress(Long userId, String addressLine1, String addressLine2, String city, String state,
			String country, int postalCode) {
		super();
		this.userId = userId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
	}

}
