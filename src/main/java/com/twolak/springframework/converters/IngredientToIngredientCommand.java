/**
 * 
 */
package com.twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.domain.Ingredient;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
	
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}
	
	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (source == null) {
			return null;
		}
		final IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		ingredientCommand.setAmount(source.getAmount());
		ingredientCommand.setDescription(source.getDescription());
		ingredientCommand.setUnitOfMeasure(this.unitOfMeasureToUnitOfMeasureCommand.convert(source.getUnitOfMeasure()));
		return ingredientCommand;
	}

}
