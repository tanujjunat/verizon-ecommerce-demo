package com.springcloud.product.subscriber;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.springcloud.product.utilities.ProductUtility;

@Component
public class ProductSubscriber implements MessageListener {
	
	@Autowired
	private ProductUtility productUtility;

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
        
        	try {
        		productUtility.updateInventoryAndSendConfirmation(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    	
}