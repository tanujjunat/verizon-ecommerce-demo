package com.springcloud.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	
	private String orderId;
	private Boolean inventoryUpdated;
	private Integer quantity;
	private Integer amount;
	private Long itemId;

}
