package com.springcloud.order.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.order.model.OrderResponse;
import com.springcloud.order.model.Orders;
import com.springcloud.order.model.Payment;
import com.springcloud.order.model.PaymentBO;
import com.springcloud.order.model.PaymentResponse;
import com.springcloud.order.model.Product;
import com.springcloud.order.model.ProductBO;
import com.springcloud.order.model.ProductResponse;
import com.springcloud.order.model.User;
import com.springcloud.order.model.UserBO;
import com.springcloud.order.model.UserResponse;
import com.springcloud.order.publisher.PaymentPublisher;
import com.springcloud.order.publisher.ProductPublisher;
import com.springcloud.order.publisher.UserPublisher;
import com.springcloud.order.repository.OrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
	
	private Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentPublisher paymentPublisher;
	
	@Autowired
	private UserPublisher userPublisher;
	
	@Autowired
	private ProductPublisher productPublisher;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private DatabaseClient client;
	
	
	public Mono<Orders> persistInDB() {
		Orders order = new Orders();
		String orderId = UUID.randomUUID().toString();
		order.setOrderId(orderId);
		return orderRepository.save(order);
	}

	public ResponseEntity<OrderResponse> createOrderResponse(Orders order, HttpStatus status) {
		OrderResponse response = new OrderResponse();
		response.setOrderId(order.getOrderId());
		response.setItemId(order.getItemId());
		response.setPaymentId(order.getPaymentId());
		response.setUserId(order.getUserId());
		response.setAmount(order.getAmount());
		response.setQuantity(order.getQuantity());
		if (order.getInventoryUpdated() != null) {
			response.setInventoryUpdated(order.getInventoryUpdated());
		} else {
			response.setInventoryUpdated(Boolean.FALSE);
		}
		
		if (order.getShippingAddressUpdated() != null) {
			response.setShippingAddressUpdated(order.getShippingAddressUpdated());
		} else {
			response.setShippingAddressUpdated(Boolean.FALSE);
		}
		
		if (response.getPaymentId() != null 
				&& Boolean.TRUE.equals(response.getInventoryUpdated())
				&& Boolean.TRUE.equals(response.getShippingAddressUpdated())) {
			response.setStatus("SUCCESS");
		} else {
			response.setStatus("PENDING");
		}
		return new ResponseEntity<>(response, status);
	}
	
	public Mono<Orders> findByOrderId(String order_id) {	
		
		String sqlQuery = "select * from orders where order_id = '%s'";
		
		return client.execute(String.format(sqlQuery, order_id)).as(Orders.class).fetch().all().next();
		
		//rowsUpdated.subscribe(num -> logger.info("Number of Rows Updated : " + num));
	}
	
	public void updatePaymentId(PaymentResponse paymentResponse) {
		
		String sqlQuery = "update orders set payment_id = '%s' where order_id = '%s'";
		
		Mono<Integer> rowsUpdated = client.execute(String.format(sqlQuery, paymentResponse.getPaymentId(), paymentResponse.getOrderId()))
		        .fetch().rowsUpdated();
		
		rowsUpdated.subscribe(num -> logger.info("Number of Rows Updated : " + num));
		
	}
	
	public Orders publishForPaymentConfirmation(Payment payment, Orders order) {
		
		PaymentBO paymentBO = new PaymentBO(order.getOrderId(), payment.getTypes(), payment.getAmount()); 
		
		String message = null;
		try {
			message = mapper.writeValueAsString(paymentBO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		paymentPublisher.publish(message);
		return order;
	}
	
	public Orders publishForQuantitySold(Product product, Orders order) {
		
		ProductBO producttBO = new ProductBO(order.getOrderId(), product.getItemId(), product.getQuantity(), product.getAmount()); 
		
		String message = null;
		try {
			message = mapper.writeValueAsString(producttBO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		productPublisher.publish(message);
		return order;
	}
	
	public Orders publishForUserTable(User user, Orders order) {
		
		UserBO userBO = new UserBO(order.getOrderId(), user.getUserId(), user.getFirstName()); 
		
		String message = null;
		try {
			message = mapper.writeValueAsString(userBO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		userPublisher.publish(message);
		return order;
	}
	
	public void updateOrderAccepted(ProductResponse productResponse) {
		
		String sqlQuery = "update orders set inventory_updated = '%s', quantity = '%s', amount = '%s', item_id = '%s' where order_id = '%s'";
		
		Mono<Integer> rowsUpdated = client.execute(String.format(sqlQuery, productResponse.getInventoryUpdated(), 
				productResponse.getQuantity(), productResponse.getAmount(), productResponse.getItemId(), productResponse.getOrderId()))
		        .fetch().rowsUpdated();
		
		rowsUpdated.subscribe(num -> logger.info("Number of Rows Updated : " + num));
		
	}

	public void updateUserDeliveryStatus(UserResponse userResponse) {
	
		String sqlQuery = "update orders set shipping_address_updated = '%s', user_id = '%s' where order_id = '%s'";
	
		Mono<Integer> rowsUpdated = client.execute(String.format(sqlQuery, userResponse.getShippingAddressUpdated(), 
				userResponse.getUserId(), userResponse.getOrderId()))
	        .fetch().rowsUpdated();
	
		rowsUpdated.subscribe(num -> logger.info("Number of Rows Updated : " + num));
	
	}

}
