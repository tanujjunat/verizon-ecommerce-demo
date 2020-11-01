package com.ecom.UserInfoService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.UserInfoService.models.AvailabilityStatus;
import com.ecom.UserInfoService.models.User;
import com.ecom.UserInfoService.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
	
	private UserRepository userRepo;

	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@PostMapping("/v1/users/create")
	public Mono<HttpStatus> create(@RequestBody User user) {
		return userRepo.save(user).thenReturn(HttpStatus.CREATED);
	}

	@GetMapping("/v1/users/allUsers")
	public Flux<User> getUsers() {
		return userRepo.findAll();
	}

	@GetMapping("/v1/users/{id}")
	public Mono<User> getUserById(@PathVariable("id") Long id) {
		return userRepo.findById(id);
	}
	
	@GetMapping("/v1/users/{id}/valid")
	public Mono<AvailabilityStatus> checkUserById(@PathVariable("id") Long id) {
		return userRepo.findById(id).hasElement().map(user -> new AvailabilityStatus(user));
		

	}

}
