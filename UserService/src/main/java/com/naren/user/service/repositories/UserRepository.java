package com.naren.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naren.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
