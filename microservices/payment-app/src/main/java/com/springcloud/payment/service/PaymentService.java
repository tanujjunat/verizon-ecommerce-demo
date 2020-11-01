package com.springcloud.payment.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.payment.model.Payment;
import com.springcloud.payment.model.PaymentResponse;
import com.springcloud.payment.publisher.PaymentPublisher;
import com.springcloud.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentPublisher publisher;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	public void sendPaymentConfirmation(Message message) throws JsonParseException, JsonMappingException, IOException {
		
		Payment payment = mapper.readValue(message.getBody(), Payment.class);
		
		PaymentResponse resp = createPaymentResponse(payment);
		saveInDB(resp);
		sendConfirmation(resp);
	}
	

	private PaymentResponse createPaymentResponse(Payment paym) {
		return new PaymentResponse(null, UUID.randomUUID().toString(), 
				paym.getOrderId(), paym.getTypes(),paym.getAmount());
	}

	private void sendConfirmation(PaymentResponse response) {
		String responseString = null;
		try {
			responseString = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		logger.info("Going to Publish back response");
		publisher.publish(responseString);

	}


	private void saveInDB(PaymentResponse response) {
		logger.info("saving to DB");
		paymentRepository.save(response);
	}

}
