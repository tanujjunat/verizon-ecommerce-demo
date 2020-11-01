package com.springcloud.product.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.springcloud.product.models.Items;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ItemsRepository extends ReactiveCrudRepository<Items, Long>  {
	
	@Query("select * from items where productid = :productid")
	Flux<Items> findAllByItems(Long productid);
	
	@Query("select * from items where id = :itemsid")
	Mono<Items> findAllById(Long itemsid);
	
}
