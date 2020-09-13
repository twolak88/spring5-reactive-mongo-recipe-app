package com.twolak.springframework.services;

import com.twolak.springframework.commands.IngredientCommand;

import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public interface IngredientService {
	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String id);
	Mono<IngredientCommand> save(IngredientCommand ingredientCommand);
	Mono<Void> deleteById(String recipeId, String ingredientId);
}
