package com.twolak.springframework.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.converters.RecipeCommandToRecipe;
import com.twolak.springframework.converters.RecipeToRecipeCommand;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.repositories.RecipeRepository;
import com.twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<RecipeCommand> findAll() {
		log.info("Calling findAllRecipes()");
		Set<RecipeCommand> recipes = new HashSet<>();
		this.recipeRepository.findAll().iterator().forEachRemaining(recipe -> recipes.add(this.recipeToRecipeCommand.convert(recipe)));
		return recipes;
	}
	
	@Transactional
	@Override
	public RecipeCommand findById(String id) {
		log.info("Calling findRecipeById(Long)");
		RecipeCommand recipeCommand = this.recipeToRecipeCommand.convert(this.recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Recipe found for id: " + id)));
		if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
			recipeCommand.getIngredients().forEach(ing -> ing.setRecipeId(recipeCommand.getId()));
		}
		return recipeCommand;
	}

	@Transactional
	@Override
	public RecipeCommand save(RecipeCommand recipeCommand) {
		Recipe convertedRecipe = this.recipeCommandToRecipe.convert(recipeCommand);
		
		Recipe savedRecipe = this.recipeRepository.save(convertedRecipe);
		return this.recipeToRecipeCommand.convert(savedRecipe);
	}

	@Transactional
	@Override
	public void deleteById(String id) {
		this.recipeRepository.deleteById(id);		
	}
}
