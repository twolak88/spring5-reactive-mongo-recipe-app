package com.twolak.springframework.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.converters.RecipeCommandToRecipe;
import com.twolak.springframework.converters.RecipeToRecipeCommand;
import com.twolak.springframework.repositories.reactive.RecipeReactiveRepository;
import com.twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<RecipeCommand> findAll() {
		return this.recipeReactiveRepository.findAll().map(this.recipeToRecipeCommand::convert);
	}
	
	@Override
	public Mono<RecipeCommand> findById(String id) {		
		return this.recipeReactiveRepository.findById(id).map(this.recipeToRecipeCommand::convert);
	}
	
	@Override
	public Mono<RecipeCommand> save(RecipeCommand recipeCommand) {
		return this.recipeReactiveRepository.save(this.recipeCommandToRecipe.convert(recipeCommand)).map(this.recipeToRecipeCommand::convert);
	}
	
	@Override
	public Mono<Void> deleteById(String id) {
		return this.recipeReactiveRepository.deleteById(id);
	}
}
