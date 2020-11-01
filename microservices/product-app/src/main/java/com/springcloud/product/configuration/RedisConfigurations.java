package com.springcloud.product.configuration;

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

import com.springcloud.product.publisher.MessagePublisher;
import com.springcloud.product.publisher.ProductPublisher;
import com.springcloud.product.subscriber.ProductSubscriber;

@Configuration
public class RedisConfigurations {
	
	@Autowired
	ProductSubscriber productSubscriber;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(productSubscriber);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), productSubTopic());
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
        return new ProductPublisher(redisTemplate(), productPubTopic());
    }
    
    @Bean
    ChannelTopic productSubTopic() {
        return new ChannelTopic("productpub:queue");
    }
    
    @Bean
    ChannelTopic productPubTopic() {
        return new ChannelTopic("productsub:queue");
    }

}
