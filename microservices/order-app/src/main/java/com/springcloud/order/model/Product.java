package com.springcloud.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private Long itemId;
	private Integer quantity;
	private Integer amount; 
	
}
