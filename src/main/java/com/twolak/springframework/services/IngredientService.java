package com.twolak.springframework.services;

import com.twolak.springframework.commands.IngredientCommand;

/**
 * @author twolak
 *
 */
public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String id);
	IngredientCommand save(IngredientCommand ingredientCommand);
	void deleteById(String recipeId, String ingredientId);
}
