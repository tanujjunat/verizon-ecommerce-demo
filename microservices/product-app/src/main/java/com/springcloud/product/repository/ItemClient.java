package com.springcloud.product.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springcloud.product.models.Item;

@FeignClient(name="zuul-api-gateway", fallback = ItemClientFallback.class)
@RibbonClient(name="item-service")
public interface ItemClient {
	
	@GetMapping("/v1/item/{itemid}")
	Item findById(@PathVariable("itemid") Long itemid);
	
	@GetMapping("/hello")
	String hello();

}

@Component
class ItemClientFallback implements ItemClient{
	
	Map<Long, Item> fallBackMap;
	
	public ItemClientFallback() {
		fallBackMap = new HashMap<Long, Item>();
		Item item1 = new Item();
		item1.setId(1l);
		item1.setDescription("Cotton Shirts for Summer");
		item1.setName("Shirt");
		item1.setAmount(1000.0f);
		item1.setQuantity(0);
		fallBackMap.put(item1.getId(), item1);
		Item item2 = new Item();
		item2.setId(2l);
		item2.setDescription("Winter wear for men");
		item2.setName("Blazer");
		item2.setAmount(5000.0f);
		item2.setQuantity(3);
		fallBackMap.put(item2.getId(), item2);
		Item item3 = new Item();
		item3.setId(3l);
		item3.setDescription("Silk saree for evening wear");
		item3.setName("Saree");
		item3.setAmount(7000.0f);
		item3.setQuantity(6);
		fallBackMap.put(item3.getId(), item3);
		Item item4 = new Item();
		item4.setId(4l);
		item4.setDescription("Linen trousers for summer");
		item4.setName("Pants");
		item4.setAmount(2000.0f);
		item4.setQuantity(5);
		fallBackMap.put(item4.getId(), item4);
		Item item5 = new Item();
		item5.setId(5l);
		item5.setDescription("Backpack for school going kids");
		item5.setName("Backpacks");
		item5.setAmount(1000.0f);
		item5.setQuantity(8);
		fallBackMap.put(item5.getId(), item5);
	}

	@Override
	public Item findById(Long itemid) {
		return fallBackMap.get(itemid);
	}

	@Override
	public String hello() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
