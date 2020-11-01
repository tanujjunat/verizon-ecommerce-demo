package com.ecom.UserInfoService.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.UserInfoService.models.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long>{

}
