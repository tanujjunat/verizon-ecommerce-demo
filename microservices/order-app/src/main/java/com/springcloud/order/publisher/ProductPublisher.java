package com.springcloud.order.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class ProductPublisher implements MessagePublisher {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ChannelTopic productPubTopic;

    public ProductPublisher() {
    }

    public ProductPublisher(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic productPubTopic) {
        
    	this.redisTemplate = redisTemplate;
        this.productPubTopic = productPubTopic;
        
    }

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(productPubTopic.getTopic(), message);
	}

}
