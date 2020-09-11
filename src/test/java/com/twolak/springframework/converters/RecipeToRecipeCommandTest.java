/**
 * 
 */
package com.twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.domain.Category;
import com.twolak.springframework.domain.Difficulty;
import com.twolak.springframework.domain.Ingredient;
import com.twolak.springframework.domain.Notes;
import com.twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
class RecipeToRecipeCommandTest {

	private static final String RECIPE_ID = "r1";
	private static final Integer COOK_TIME = Integer.valueOf("5");
	private static final Integer PREP_TIME = Integer.valueOf("7");
	private static final String DESCRIPTION = "My Recipe";
	private static final String DIRECTIONS = "Directions";
	private static final Difficulty DIFFICULTY = Difficulty.EASY;
	private static final Integer SERVINGS = Integer.valueOf("3");
	private static final String SOURCE = "Source";
	private static final String URL = "Some URL";
	private static final String CAT_ID_1 = "c1";
	private static final String CAT_ID2 = "c2";
	private static final String INGRED_ID_1 = "i1";
	private static final String INGRED_ID_2 = "i2";
	private static final String NOTES_ID = "n1";

	private RecipeToRecipeCommand recipeToRecipeCommand;

	@BeforeEach
	void setUp() throws Exception {
		this.recipeToRecipeCommand = new RecipeToRecipeCommand(
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new CategoryToCategoryCommand(), new NotesToNotesCommand());
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.recipeToRecipeCommand.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.recipeToRecipeCommand.convert(new Recipe()));
	}

	@Test
	void testConvert() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		recipe.setCookTime(COOK_TIME);
		recipe.setPrepTime(PREP_TIME);
		recipe.setDescription(DESCRIPTION);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setDirections(DIRECTIONS);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);

		Notes notes = new Notes();
		notes.setId(NOTES_ID);

		recipe.setNotes(notes);

		Category category = new Category();
		category.setId(CAT_ID_1);

		Category category2 = new Category();
		category2.setId(CAT_ID2);

		recipe.getCategories().add(category);
		recipe.getCategories().add(category2);

		Ingredient ingredient = new Ingredient();
		ingredient.setId(INGRED_ID_1);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(INGRED_ID_2);

		recipe.getIngredients().add(ingredient);
		recipe.getIngredients().add(ingredient2);

		RecipeCommand command = this.recipeToRecipeCommand.convert(recipe);

		assertNotNull(command);
		assertEquals(RECIPE_ID, command.getId());
		assertEquals(COOK_TIME, command.getCookTime());
		assertEquals(PREP_TIME, command.getPrepTime());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(DIFFICULTY, command.getDifficulty());
		assertEquals(DIRECTIONS, command.getDirections());
		assertEquals(SERVINGS, command.getServings());
		assertEquals(SOURCE, command.getSource());
		assertEquals(URL, command.getUrl());
		assertEquals(NOTES_ID, command.getNotes().getId());
		assertEquals(2, command.getCategories().size());
		assertEquals(2, command.getIngredients().size());
	}

}
