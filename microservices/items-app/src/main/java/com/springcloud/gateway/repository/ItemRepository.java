package com.springcloud.gateway.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.springcloud.gateway.models.Item;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, Long>  {
	
	
}
