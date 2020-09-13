package com.twolak.springframework.services;

import com.twolak.springframework.commands.RecipeCommand;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public interface RecipeService {
	Flux<RecipeCommand> findAll();
	Mono<RecipeCommand> findById(String recipeId);
	Mono<RecipeCommand> save(RecipeCommand recipeCommand);
	Mono<Void> deleteById(String id);
}
