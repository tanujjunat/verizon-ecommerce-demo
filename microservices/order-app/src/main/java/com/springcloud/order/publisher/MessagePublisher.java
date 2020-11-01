package com.springcloud.order.publisher;

public interface MessagePublisher {
	
	void publish(final String message);

}
