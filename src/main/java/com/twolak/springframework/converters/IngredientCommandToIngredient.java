/**
 * 
 */
package com.twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.domain.Ingredient;
import com.twolak.springframework.domain.Recipe;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
	
	private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure; 

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
		this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
	}
	
	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		final Ingredient ingredient = new Ingredient();
		if (source.getId() != null) {
			ingredient.setId(source.getId());
		}
		if(source.getRecipeId() != null) {
			Recipe recipe = new Recipe();
			recipe.setId(source.getRecipeId());
			recipe.addIngredient(ingredient);
		}
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUnitOfMeasure(this.unitOfMeasureCommandToUnitOfMeasure.convert(source.getUnitOfMeasure()));
		return ingredient;
	}

}
