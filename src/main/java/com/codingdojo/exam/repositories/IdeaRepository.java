package com.codingdojo.exam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.exam.models.Idea;

public interface IdeaRepository extends CrudRepository<Idea,Long>{

}
