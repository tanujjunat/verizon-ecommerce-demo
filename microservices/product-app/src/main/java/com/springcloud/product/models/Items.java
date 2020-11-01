/**
 * 
 */
package com.springcloud.product.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Items implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -546362945428914475L;
	@Id
	private Long id;
	private Long itemid;
	private Long skuid;
	private Long productid;
	private	int quantity;
	private float price;
	
	public Items() {
		
	}
	
	public Items(Long id, Long itemid, Long skuid, Long productid, int quantity, float price) {
		super();
		this.id = id;
		this.itemid = itemid;
		this.skuid = skuid;
		this.productid = productid;
		this.quantity = quantity;
		this.price = price;
	}



	public Items(Long itemid, Long skuid, Long productid,int quantity, float price) {
		this.itemid=itemid;
		this.skuid=skuid;
		this.productid=productid;
		this.quantity=quantity;
		this.price=price;

	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the itemid
	 */
	public Long getItemid() {
		return itemid;
	}

	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	/**
	 * @return the skuid
	 */
	public Long getSkuid() {
		return skuid;
	}

	/**
	 * @param skuid the skuid to set
	 */
	public void setSkuid(Long skuid) {
		this.skuid = skuid;
	}

	/**
	 * @return the productid
	 */
	public Long getProductid() {
		return productid;
	}

	/**
	 * @param productid the productid to set
	 */
	public void setProductid(Long productid) {
		this.productid = productid;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

}
