package com.springcloud.product.models;

public class AvailabilityStatus {
	
	private boolean available;

	public AvailabilityStatus(boolean status) {
		this.available = status;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}


	
	

}
