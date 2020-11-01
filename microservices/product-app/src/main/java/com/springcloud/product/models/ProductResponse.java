package com.springcloud.product.models;

public class ProductResponse {
	
	private String orderId;
	private Boolean inventoryUpdated;
	private Integer quantity;
	private Integer amount;
	private Long itemId;
	
	
	public ProductResponse(String orderId, Boolean inventoryUpdated, Integer quantity, Integer amount, Long itemId) {
		super();
		this.orderId = orderId;
		this.inventoryUpdated = inventoryUpdated;
		this.quantity = quantity;
		this.amount = amount;
		this.itemId = itemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Boolean getInventoryUpdated() {
		return inventoryUpdated;
	}
	public void setInventoryUpdated(Boolean inventoryUpdated) {
		this.inventoryUpdated = inventoryUpdated;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	

}
