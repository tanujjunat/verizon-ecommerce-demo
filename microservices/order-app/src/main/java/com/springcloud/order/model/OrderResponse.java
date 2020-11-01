package com.springcloud.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderResponse {
	
	private String OrderId;
	private String userId;
	private String paymentId;
	private String itemId;
	private Integer quantity;
	private Integer amount;
	private String status;
	private Boolean inventoryUpdated;
	private Boolean shippingAddressUpdated;
	private String error;

}
