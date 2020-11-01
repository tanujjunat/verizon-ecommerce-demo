package com.springcloud.order.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class UserPublisher implements MessagePublisher {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ChannelTopic userPubTopic;

    public UserPublisher() {
    }

    public UserPublisher(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic userPubTopic) {
        
    	this.redisTemplate = redisTemplate;
        this.userPubTopic = userPubTopic;
        
    }

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(userPubTopic.getTopic(), message);
	}

}
