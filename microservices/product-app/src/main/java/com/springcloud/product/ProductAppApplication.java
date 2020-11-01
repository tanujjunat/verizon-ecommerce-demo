package com.springcloud.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ProductAppApplication {
	
	@Value("${api.auth.header}")
	private String authHeader;
	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}
	
	@Bean
	AuthInterceptor authFeign() {
	    return new AuthInterceptor();
	}
	
	class AuthInterceptor implements RequestInterceptor {

	    @Override
	    public void apply(RequestTemplate template) {
	        final String KEY_AUTHORIZATION = "Authorization";
			template.header(KEY_AUTHORIZATION, authHeader);

	    }

	}
}
