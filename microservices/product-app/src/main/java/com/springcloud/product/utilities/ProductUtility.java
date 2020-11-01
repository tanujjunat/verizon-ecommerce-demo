package com.springcloud.product.utilities;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.product.exception.ApiRequestException;
import com.springcloud.product.models.AvailabilityStatus;
import com.springcloud.product.models.Items;
import com.springcloud.product.models.ProductBO;
import com.springcloud.product.models.ProductResponse;
import com.springcloud.product.publisher.ProductPublisher;
import com.springcloud.product.repository.ItemsRepository;

import reactor.core.publisher.Mono;

@Component
public class ProductUtility {
	
	Logger logger = LoggerFactory.getLogger(ProductUtility.class);
	
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	private ProductPublisher publisher;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	public AvailabilityStatus checkQuantity (int qty){
		return new AvailabilityStatus(qty > 0);		
	}
	
	public Items updateQuantity(Long id) {
		
		return itemsRepository.findAllById(id).flatMap(items -> {
			return itemsRepository.save(updateProduct(items));
			}).block(); 
	
	}
	
	public Items updateProduct(Items p) {
		Items updatedProduct = new Items(p.getId(), p.getItemid(), p.getSkuid(), p.getProductid(), p.getQuantity(), p.getPrice());
		if(p.getQuantity()>0) {
			updatedProduct.setQuantity(p.getQuantity() - 1);
			return updatedProduct;
		} else {
			throw new ApiRequestException("Product is sold out");
		}
	}
	
	public void updateInventoryAndSendConfirmation(Message message) throws JsonParseException, JsonMappingException, IOException {
		
		ProductBO product = mapper.readValue(message.getBody(), ProductBO.class);		
		
		updateQuantity(product.getItemId());
		ProductResponse resp = createProductResponse(product);
		sendConfirmation(resp);
		
	}

	private ProductResponse sendConfirmation(ProductResponse response) {
		
		String responseString = null;
		try {
			responseString = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		publisher.publish(responseString);

		
		return response;
	}

	private ProductResponse createProductResponse(ProductBO product) {
		// TODO Auto-generated method stub
		return new ProductResponse(product.getOrderId(), Boolean.TRUE , product.getQuantity(), product.getAmount(), product.getItemId());
	}

}
