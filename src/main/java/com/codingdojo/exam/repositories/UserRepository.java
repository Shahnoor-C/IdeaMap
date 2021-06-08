package com.codingdojo.exam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.exam.models.User;


public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
}

