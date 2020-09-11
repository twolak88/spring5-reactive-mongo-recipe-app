/**
 * 
 */
package com.twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.domain.Ingredient;

/**
 * @author twolak
 *
 */
class IngredientCommandToIngredientTest {
	
	private static final BigDecimal AMOUNT = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final String ID_VALUE = "i1";
	private static final String UOM_ID = "u1";

	private IngredientCommandToIngredient ingredientCommandToIngredient;

	@BeforeEach
	void setUp() throws Exception {
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
				new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.ingredientCommandToIngredient.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.ingredientCommandToIngredient.convert(new IngredientCommand()));
	}

	@Test
	void testConvert() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_VALUE);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(UOM_ID);
		ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

		Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);

		assertNotNull(ingredient);
		assertNotNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
	}
	
	@Test
	void testConvertWithNullUom() {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_VALUE);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
		ingredientCommand.setUnitOfMeasure(null);

		Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);

		assertNotNull(ingredient);
		assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
	}

}
