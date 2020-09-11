/**
 * 
 */
package com.twolak.springframework.converters;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.domain.Recipe;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final CategoryToCategoryCommand categoryToCategoryCommand;
	private final NotesToNotesCommand notesToNotesCommand;

	public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientToIngredientCommand,
			CategoryToCategoryCommand categoryToCategoryCommand, NotesToNotesCommand notesToNotesCommand) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
		this.notesToNotesCommand = notesToNotesCommand;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotes(this.notesToNotesCommand.convert(source.getNotes()));
		if (source.getIngredients() != null && source.getIngredients().size() > 0){
			source.getIngredients().forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
		}
		if (source.getCategories() != null && source.getCategories().size() > 0){
			source.getCategories().forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
		}
		return recipeCommand;
	}
}
