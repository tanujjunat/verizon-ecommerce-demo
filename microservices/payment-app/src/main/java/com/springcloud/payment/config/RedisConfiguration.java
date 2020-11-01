package com.springcloud.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.springcloud.payment.publisher.MessagePublisher;
import com.springcloud.payment.publisher.PaymentPublisher;
import com.springcloud.payment.subscriber.PaymentSubscriber;

@Configuration
public class RedisConfiguration {
	
	@Autowired
	PaymentSubscriber paymentSubscriber;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(paymentSubscriber);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), subscriberPaymentTopic());
        return container;
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }
	
	@Bean
    MessagePublisher redisPublisher() {
        return new PaymentPublisher(redisTemplate(), publisherPaymentTopic());
    }
    
    @Bean
    ChannelTopic subscriberPaymentTopic() {
        return new ChannelTopic("paymentpub:queue");
    }
    
    @Bean
    ChannelTopic publisherPaymentTopic() {
        return new ChannelTopic("paymentsub:queue");
    }

}
