package com.springcloud.order.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.springcloud.order.model.Orders;

import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Orders, Integer> {
	
	@Query("select * from orders where order_id = $1")
    Mono<Orders> findByOrderId(String order_id);

}
