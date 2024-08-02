package com.naren.user.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.naren.user.service.entities.Hotel;
import com.naren.user.service.entities.Rating;
import com.naren.user.service.entities.User;
import com.naren.user.service.exceptions.ResourceNotFoundException;
import com.naren.user.service.external.services.HotelService;
import com.naren.user.service.repositories.UserRepository;
import com.netflix.discovery.converters.Auto;

import ch.qos.logback.classic.Logger;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		
		for(User user : users) {
			ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), ArrayList.class);
			user.setRatings(ratingsOfUser);
		}
		
		return users;
	}

	@Override
	public User getUser(String userId) {
		
		 
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server: "+userId));
		
		//fetch rating of the above user from RATING service
//		http://localhost:8083/ratings/users/76aca794-c0b1-489d-95be-b6d446cba198
		
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		List<Rating> ratingList = ratings.stream().map( rating-> {
			
			//api call to hotel service to get the hotel
			//http://localhost:8082/hotels/b6126c61-8f2c-4b33-b01e-59dd5fa3aa4f
			 //ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			 Hotel hotel = hotelService.getHotel(rating.getHotelId());
			 //logger.info("Response Status Code : {}"+forEntity.getStatusCode());
			 rating.setHotel(hotel);
			 
			return rating;
		}).collect(Collectors.toList());
		
		
		
		
		
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User userId) {
		// TODO Auto-generated method stub
		
	}

}
