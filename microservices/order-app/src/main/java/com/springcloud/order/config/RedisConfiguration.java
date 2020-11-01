package com.springcloud.order.config;

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

import com.springcloud.order.publisher.MessagePublisher;
import com.springcloud.order.publisher.PaymentPublisher;
import com.springcloud.order.subscriber.PaymentSubscriber;
import com.springcloud.order.subscriber.ProductSubscriber;
import com.springcloud.order.subscriber.UserSubscriber;


@Configuration
public class RedisConfiguration {
	
	@Autowired
	PaymentSubscriber paymentSubscriber;
	
	@Autowired
	UserSubscriber userSubscriber;
	
	@Autowired
	ProductSubscriber productSubscriber;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

    @Bean
    MessageListenerAdapter paymentMessageListener() {
        return new MessageListenerAdapter(paymentSubscriber);
    }

    @Bean
    RedisMessageListenerContainer paymentRedisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(paymentMessageListener(), subscriberPaymentTopic());
        return container;
    }
    
    @Bean
    MessageListenerAdapter userMessageListener() {
        return new MessageListenerAdapter(userSubscriber);
    }

    @Bean
    RedisMessageListenerContainer userRedisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(userMessageListener(), userSubTopic());
        return container;
    }
    
    @Bean
    MessageListenerAdapter productMessageListener() {
        return new MessageListenerAdapter(productSubscriber);
    }

    @Bean
    RedisMessageListenerContainer productRedisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(productMessageListener(), productSubTopic());
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
    MessagePublisher redisUserPublisher() {
        return new PaymentPublisher(redisTemplate(), userPubTopic());
    }
	
	@Bean
    MessagePublisher redisProductPublisher() {
        return new PaymentPublisher(redisTemplate(), productPubTopic());
    }
    
    @Bean
    ChannelTopic subscriberPaymentTopic() {
        return new ChannelTopic("paymentsub:queue");
    }
    
    @Bean
    ChannelTopic publisherPaymentTopic() {
        return new ChannelTopic("paymentpub:queue");
    }
    
    @Bean
    ChannelTopic productPubTopic() {
        return new ChannelTopic("productpub:queue");
    }
    
    @Bean
    ChannelTopic productSubTopic() {
        return new ChannelTopic("productsub:queue");
    }
    
    @Bean
    ChannelTopic userPubTopic() {
        return new ChannelTopic("userpub:queue");
    }
    
    @Bean
    ChannelTopic userSubTopic() {
        return new ChannelTopic("usersub:queue");
    }

}
