package com.springcloud.payment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
@Entity
public class PaymentResponse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer serialId;
	
	private String paymentId;
	private String orderId;
	private String types;
	private Integer amount;

}
