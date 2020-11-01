package com.springcloud.order.subscriber;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.order.model.ProductResponse;
import com.springcloud.order.service.OrderService;

@Component
public class ProductSubscriber implements MessageListener {
	
	Logger logger = LoggerFactory.getLogger(ProductSubscriber.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		
		logger.info("Got confirmation from Product App");
		
		ProductResponse productResponse = null;
		try {
			productResponse = mapper.readValue(message.getBody(), ProductResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		orderService.updateOrderAccepted(productResponse);
		
	}

}
