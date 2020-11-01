package com.springcloud.payment.publisher;

public interface MessagePublisher {
	
	void publish(final String message);

}
