package com.springcloud.payment.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class PaymentPublisher implements MessagePublisher {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ChannelTopic publisherPaymentTopic;

    public PaymentPublisher() {
    }

    public PaymentPublisher(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic publisherPaymentTopic) {
        
    	this.redisTemplate = redisTemplate;
        this.publisherPaymentTopic = publisherPaymentTopic;
        
    }

    public void publish(final String message) {
    	
    	redisTemplate.convertAndSend(publisherPaymentTopic.getTopic(), message);

    }

}
