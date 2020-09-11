/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.converters.IngredientCommandToIngredient;
import com.twolak.springframework.converters.IngredientToIngredientCommand;
import com.twolak.springframework.domain.Ingredient;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.repositories.RecipeRepository;
import com.twolak.springframework.repositories.UnitOfMeasureRepository;
import com.twolak.springframework.services.IngredientService;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		Recipe recipe = getRecipeById(recipeId);
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst().map(ingredientToIngredientCommand::convert);
		if (!ingredientCommandOptional.isPresent()) {
			String msg = "Ingredient for id: " + ingredientId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipeId);
		return ingredientCommand; 
	}

	@Override
	public IngredientCommand save(IngredientCommand ingredientCommand) {
		Recipe recipe = getRecipeById(ingredientCommand.getRecipeId());
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		if (ingredientOptional.isPresent()) {
			Ingredient ingredient = ingredientOptional.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			ingredient.setUnitOfMeasure(
					this.unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
							.orElseThrow(() -> new NotFoundException("Unit of measure not found!")));
		} else {
			recipe.addIngredient(this.ingredientCommandToIngredient.convert(ingredientCommand));

		}
		Recipe savedRecipe = this.recipeRepository.save(recipe);

		Optional<Ingredient> savedingredientOptional = savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		if (!savedingredientOptional.isPresent()) {
			
			savedingredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
					.filter(ingredient -> ingredient.getUnitOfMeasure().getId()
							.equals(ingredientCommand.getUnitOfMeasure().getId()))
					.findFirst();
			if (!savedingredientOptional.isPresent()) {
				String msg = "Ingredient for id: " + ingredientCommand.getId() + " not found!";
				log.debug(msg);
				throw new NotFoundException(msg);
			}
		}
		IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedingredientOptional.get());
        ingredientCommandSaved.setRecipeId(recipe.getId());
		return ingredientCommandSaved;
	}

	private Recipe getRecipeById(String recipeId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + recipeId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
		return recipeOptional.get();
	}

	@Override
	public void deleteById(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + recipeId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
		
		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> ingredientToDeleteOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
		
		if (!ingredientToDeleteOptional.isPresent()) {
			String msg  = "Ingredient for id: " + ingredientId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
		Ingredient ingredientToDelete = ingredientToDeleteOptional.get();
		recipe.getIngredients().remove(ingredientToDelete);
		this.recipeRepository.save(recipe);
	}
}
