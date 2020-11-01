package com.ecom.UserInfoService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.UserInfoService.models.ShippingAddress;
import com.ecom.UserInfoService.repository.ShippingRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
	
	@Autowired
	ShippingRepository shippingRepo;
	
	@GetMapping("/{id}")
	public Mono<ShippingAddress> getShippingAddress(@PathVariable("id") Long id) {
		return shippingRepo.findById(id);
		
	}
	
	@PostMapping("/create")
	public Mono<ShippingAddress> createAddress(@RequestBody ShippingAddress address){
		return shippingRepo.save(address);
	}

}
