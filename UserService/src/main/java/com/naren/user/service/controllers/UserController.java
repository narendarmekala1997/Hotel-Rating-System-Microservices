package com.naren.user.service.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naren.user.service.entities.User;
import com.naren.user.service.services.UserService;
import com.naren.user.service.services.UserServiceImpl;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import ch.qos.logback.classic.Logger;
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		User user1 =userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
		
	}
	
	@GetMapping("/{userId}")
	//@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	//@Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name="userRateLimiter",fallbackMethod="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		//logger.info("this is coming here............*************************************");
		logger.info(""+(retryCount++));
		User user = userService.getUser(userId);
		
		return ResponseEntity.ok(user);
		
	}
	int retryCount=1;
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		
		ex.printStackTrace();
		logger.info("Fall Back is excecuted because service is down :"+ex.getMessage());
		User user=User.builder().email("dummy@gmail.com").name("Dummy").about("This use is created dummy because some service is down").build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		
		return ResponseEntity.ok(allUser);
	}

}
