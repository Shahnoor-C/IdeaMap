package com.codingdojo.exam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.exam.models.UserIdea;

public interface UserIdeaRepository extends CrudRepository<UserIdea,Long>{

}
