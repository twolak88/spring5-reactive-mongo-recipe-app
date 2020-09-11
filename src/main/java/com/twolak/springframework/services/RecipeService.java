package com.twolak.springframework.services;

import java.util.Set;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeService {
	Set<RecipeCommand> findAll();
	RecipeCommand findById(String recipeId);
	RecipeCommand save(RecipeCommand recipeCommand);
	void deleteById(String id);
}
