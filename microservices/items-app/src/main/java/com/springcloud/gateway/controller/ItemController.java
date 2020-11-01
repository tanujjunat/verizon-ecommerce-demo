package com.springcloud.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.gateway.models.Item;
import com.springcloud.gateway.repository.ItemRepository;

import reactor.core.publisher.Mono;

@RestController
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
	@GetMapping("/v1/item/{id}")
	public Mono<Item> getSingleProduct(@PathVariable Long id) {
		
		return itemRepository.findById(id).map(item ->item)
				.defaultIfEmpty(new Item());

	}
}
