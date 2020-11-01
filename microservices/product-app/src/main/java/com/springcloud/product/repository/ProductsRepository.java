package com.springcloud.product.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.springcloud.product.models.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductsRepository extends ReactiveCrudRepository<Product, Long>  {
	
	@Query("select * from product where category = :category")
	Flux<Product> findAllByCategory(String category);
	
}
