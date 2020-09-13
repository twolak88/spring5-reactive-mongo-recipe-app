/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.converters.IngredientCommandToIngredient;
import com.twolak.springframework.converters.IngredientToIngredientCommand;
import com.twolak.springframework.domain.Ingredient;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.domain.UnitOfMeasure;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.repositories.reactive.RecipeReactiveRepository;
import com.twolak.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.twolak.springframework.services.IngredientService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
	}

	@Override
	public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		
		return this.recipeReactiveRepository
				.findById(recipeId)
				.flatMapIterable(Recipe::getIngredients)
				.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
				.single()
				.map(ingredient -> {
					IngredientCommand ingredientCommand = this.ingredientToIngredientCommand.convert(ingredient);
					ingredientCommand.setRecipeId(recipeId);
					return ingredientCommand;
				});
		
//		return this.recipeReactiveRepository.findById(recipeId)
//				.map(recipe -> recipe.getIngredients().stream()
//						.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)).findFirst())
//				.filter(Optional::isPresent)
//				.map(ingredient -> {
//					IngredientCommand ingredientCommand = this.ingredientToIngredientCommand.convert(ingredient.get());
//					ingredientCommand.setRecipeId(recipeId);
//					return ingredientCommand;
//				});
		
//		Recipe recipe = getRecipeById(recipeId);
//		
//		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
//				.findFirst().map(ingredientToIngredientCommand::convert);
//		if (!ingredientCommandOptional.isPresent()) {
//			String msg = "Ingredient for id: " + ingredientId + " not found!";
//			log.debug(msg);
//			throw new NotFoundException(msg);
//		}
//        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
//        ingredientCommand.setRecipeId(recipeId);
//		return Mono.just(ingredientCommand); 
	}

	@Override
	public Mono<IngredientCommand> save(IngredientCommand ingredientCommand) {		
		Recipe recipe = getRecipeById(ingredientCommand.getRecipeId());
		Optional<Ingredient> ingredientOptional = recipe.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
				.findFirst();
		if (ingredientOptional.isPresent()) {
			UnitOfMeasure unitOfMeasure = this.unitOfMeasureReactiveRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).block();
			
			if (unitOfMeasure == null) {
				throw new RuntimeException("Unit of measure not found");
			}
			
			Ingredient ingredient = ingredientOptional.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			ingredient.setUnitOfMeasure(unitOfMeasure);
		} else {
			Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
			ingredient.setUnitOfMeasure(this.unitOfMeasureReactiveRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).block());
			recipe.addIngredient(ingredient);

		}
		Recipe savedRecipe = this.recipeReactiveRepository.save(recipe).block();

		Optional<Ingredient> savedingredientOptional = savedRecipe.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
				.findFirst();
		if (!savedingredientOptional.isPresent()) {
			
			savedingredientOptional = savedRecipe.getIngredients()
					.stream()
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
		return Mono.just(ingredientCommandSaved);
	}

	private Recipe getRecipeById(String recipeId) {
		Optional<Recipe> recipeOptional = this.recipeReactiveRepository.findById(recipeId).blockOptional();
		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + recipeId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
		return recipeOptional.get();
	}

	@Override
	public Mono<Void> deleteById(String recipeId, String ingredientId) {
		
//		Recipe recipez = this.recipeRepository.findById(recipeId).get();
//		Recipe reciper = this.recipeReactiveRepository.findById(recipeId).block();
		
		Optional<Recipe> recipeOptional = this.recipeReactiveRepository.findById(recipeId).blockOptional();
		
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
		this.recipeReactiveRepository.save(recipe).block();
		return Mono.empty();
	}
}
