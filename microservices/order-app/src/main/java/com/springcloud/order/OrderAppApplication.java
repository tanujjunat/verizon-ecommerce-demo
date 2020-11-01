package com.springcloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAppApplication.class, args);
	}
	
	@Bean
	AuthInterceptor authFeign() {
	    return new AuthInterceptor();
	}
	
	class AuthInterceptor implements RequestInterceptor {

	    @Override
	    public void apply(RequestTemplate template) {
	        final String KEY_AUTHORIZATION = "Authorization";
			template.header(KEY_AUTHORIZATION, "Basic YWRtaW46cm9oaXQ=");

	    }

	}

}
