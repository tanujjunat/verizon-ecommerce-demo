package com.springcloud.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springcloud.payment.model.PaymentResponse;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentResponse, Integer>{

	
}
