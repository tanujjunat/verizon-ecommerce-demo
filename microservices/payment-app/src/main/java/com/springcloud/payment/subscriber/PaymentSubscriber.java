package com.springcloud.payment.subscriber;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.springcloud.payment.service.PaymentService;

@Component
public class PaymentSubscriber implements MessageListener {
	
	@Autowired
	private PaymentService paymentService;

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
        
        	try {
				paymentService.sendPaymentConfirmation(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    	
}