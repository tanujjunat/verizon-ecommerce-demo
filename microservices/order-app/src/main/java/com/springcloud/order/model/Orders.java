package com.springcloud.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Orders {
	
	@Id
	private Integer serialId;
	private String orderId;
	private String userId;
	private String paymentId;
	private String itemId;
	private Integer quantity;
	private Integer amount;
	private Boolean inventoryUpdated;
	private Boolean shippingAddressUpdated;

}
