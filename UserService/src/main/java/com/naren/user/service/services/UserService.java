package com.naren.user.service.services;

import java.util.List;

import com.naren.user.service.entities.User;

public interface UserService {
	
	User saveUser(User user);
	 
	List<User> getAllUser();
	
	User getUser(String userId);
	
	User updateUser(User user);
	
	void deleteUser(User userId);

}
