package com.springcloud.order.subscriber;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.order.model.PaymentResponse;
import com.springcloud.order.service.OrderService;

@Component
public class PaymentSubscriber implements MessageListener {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		
		
		
		PaymentResponse paymentResponse = null;
		try {
			paymentResponse = mapper.readValue(message.getBody(), PaymentResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		orderService.updatePaymentId(paymentResponse);
		
	}

}
