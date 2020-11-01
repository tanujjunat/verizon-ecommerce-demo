package com.springcloud.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {
	
	private String orderId;
	private Long userId;
	private String firstName;

}
