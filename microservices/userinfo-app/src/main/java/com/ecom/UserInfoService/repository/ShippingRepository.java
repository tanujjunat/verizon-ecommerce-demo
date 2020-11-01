package com.ecom.UserInfoService.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ecom.UserInfoService.models.ShippingAddress;

public interface ShippingRepository extends ReactiveCrudRepository<ShippingAddress, Long>{

}
