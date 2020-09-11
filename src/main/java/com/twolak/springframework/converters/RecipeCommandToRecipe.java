package com.twolak.springframework.converters;

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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
	
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final CategoryCommandToCategory categoryCommandToCategory;
	private final NotesCommandToNotes notesCommandToNotes;

	public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
			CategoryCommandToCategory categoryCommandToCategory, NotesCommandToNotes notesCommandToNotes) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.categoryCommandToCategory = categoryCommandToCategory;
		this.notesCommandToNotes = notesCommandToNotes;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setImage(source.getImage());
		recipe.setNotes(this.notesCommandToNotes.convert(source.getNotes()));
		if (source.getIngredients() != null && source.getIngredients().size() > 0){
			recipe.setIngredients(source.getIngredients().stream().map(this.ingredientCommandToIngredient::convert)
				.collect(Collectors.toSet()));
		}
		if (source.getCategories() != null && source.getCategories().size() > 0){
			recipe.setCategories(source.getCategories().stream().map(this.categoryCommandToCategory::convert)
				.collect(Collectors.toSet()));
		}
		return recipe;
	}

}
