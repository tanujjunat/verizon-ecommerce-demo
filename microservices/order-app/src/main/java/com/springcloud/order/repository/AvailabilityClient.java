package com.springcloud.order.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springcloud.order.model.AvailabilityStatus;

@FeignClient(name="zuul-api-gateway")
//@RibbonClient(name="product-service")
public interface AvailabilityClient {
	
	@GetMapping("/v1/products/{id}/availablity")
	AvailabilityStatus checkAvailability(@PathVariable("id") Long id);

}
