package com.springcloud.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springcloud.order.model.AvailabilityStatus;
import com.springcloud.order.model.OrderRequest;
import com.springcloud.order.model.OrderResponse;
import com.springcloud.order.model.Payment;
import com.springcloud.order.model.Product;
import com.springcloud.order.model.User;
import com.springcloud.order.repository.AvailabilityClient;
import com.springcloud.order.service.OrderService;

import reactor.core.publisher.Mono;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AvailabilityClient availabilityClient;

	
	@PostMapping("/v1/order")
    public Mono<ResponseEntity<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
		
		Product product = orderRequest.getProduct();
		Payment payment = orderRequest.getPayment();
		User user = orderRequest.getUser();
		
		return Mono.just(availabilityClient.checkAvailability(product.getItemId()))
			.flatMap(status->{
				if(status.getAvailable()) {
					return createSuccessfulResponse(payment, product, user);
				}
				return Mono.just(notAvailableResponse());
			});		
      }

	private Mono<ResponseEntity<OrderResponse>> createSuccessfulResponse(Payment payment, Product product, User user) {		
		return orderService.persistInDB()
			.map(order -> orderService.publishForPaymentConfirmation(payment, order))
			.map(order -> orderService.publishForQuantitySold(product, order))
			.map(order -> orderService.publishForUserTable(user, order))
			.map(order -> orderService.createOrderResponse(order, HttpStatus.CREATED));
	}

	private ResponseEntity<OrderResponse> notAvailableResponse() {
		OrderResponse unavailableResponse = new OrderResponse();
		unavailableResponse.setError("Product not available");
		return new ResponseEntity<>(unavailableResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@GetMapping("/v1/order/{id}")
    public Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable String id) {
	
        return orderService.findByOrderId(id)
        		.map(order -> orderService.createOrderResponse(order, HttpStatus.OK))
        		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        
    }
	
	

}
