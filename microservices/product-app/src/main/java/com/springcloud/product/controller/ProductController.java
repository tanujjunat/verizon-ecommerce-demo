package com.springcloud.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.product.exception.ApiRequestException;
import com.springcloud.product.models.AvailabilityStatus;
import com.springcloud.product.models.Item;
import com.springcloud.product.models.Items;
import com.springcloud.product.models.Product;
import com.springcloud.product.repository.ItemClient;
import com.springcloud.product.repository.ItemsRepository;
import com.springcloud.product.repository.ProductsRepository;
import com.springcloud.product.utilities.ProductUtility;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {

	@Autowired
	ProductsRepository productsRepository;
	
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	ProductUtility productUtility;
	
	@Autowired
	ItemClient itemClient;

	@GetMapping("/v1/products")
	public Flux<Product> getAllProducts(@RequestParam Optional<String> category) {
		if(category.isPresent()) {

		return productsRepository.findAllByCategory(category.get().toLowerCase())
				.switchIfEmpty(Flux.error(new ApiRequestException("No Products found")));
		} else {
			return productsRepository.findAll()
					.switchIfEmpty(Flux.error(new ApiRequestException("No Products found")));
		}

	}

	@GetMapping("/v1/products/{id}")
	public Mono<List<Item>> getSingleProductDetails(@PathVariable Long id) {
		  Flux<Items> items = itemsRepository.findAllByItems(id)
				  .switchIfEmpty(Flux.error(new ApiRequestException("No Products found")));
		  Map<Long, Items> listOfItems = new HashMap<>();
		  return items.map(item->{
			  listOfItems.put(item.getItemid(), item);
			  return item;
		  }).collectList().flatMap(itemsList->{
			  List<Mono<Item>> listOfMonos = new ArrayList<>();
			  listOfItems.keySet().forEach(item->
				  listOfMonos.add(Mono.just( itemClient.findById(item)))
			  );
			 return Flux.mergeSequential(listOfMonos).collectList().map(listOfIt->{
				 List<Item> item = new ArrayList<>();
				 listOfIt.forEach(it->{
					 Items itemFromMap = listOfItems.get(it.getId());
					 it.setAmount(itemFromMap.getPrice());
					 it.setQuantity(itemFromMap.getQuantity());
					 it.setItemId(itemFromMap.getId());
					 item.add(it);
				 });
				 return item;
			 });
		  });
	}
	
	@GetMapping("/v1/products/{id}/availablity")
	public Mono<AvailabilityStatus> checkAvailable(@PathVariable Long id) {
		return  itemsRepository.findAllById(id).map(item->{
			return productUtility.checkQuantity(item.getQuantity());
		}).defaultIfEmpty(productUtility.checkQuantity(0));
		
	} 
	
}
