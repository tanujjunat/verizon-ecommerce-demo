package com.springcloud.order.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
	
	@Id
	private Integer serialId;
	
	private String paymentId;
	private String orderId;
	private String types;
	private Integer amount;

}
