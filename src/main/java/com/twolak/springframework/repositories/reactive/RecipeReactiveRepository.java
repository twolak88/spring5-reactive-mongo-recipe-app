package com.twolak.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String>{

}
