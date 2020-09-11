package com.twolak.springframework.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.converters.CategoryToCategoryCommand;
import com.twolak.springframework.converters.IngredientToIngredientCommand;
import com.twolak.springframework.converters.NotesToNotesCommand;
import com.twolak.springframework.converters.RecipeToRecipeCommand;
import com.twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.repositories.RecipeRepository;

/**
 * @author twolak
 *
 */
public class RecipeServiceImplTest {

	private final String RECIPE_ID = "r1";

	private RecipeServiceImpl recipeService;

	@Mock
	private RecipeRepository recipeRepository;

	private RecipeToRecipeCommand recipeToRecipeCommand;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeToRecipeCommand = new RecipeToRecipeCommand(
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new CategoryToCategoryCommand(), new NotesToNotesCommand());
		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, null);

	}

	@Test
	public void testDeleteById() {
		String idToDelete = RECIPE_ID;
		this.recipeService.deleteById(idToDelete);

		// woid, returned nothing, so no when

		verify(this.recipeRepository, times(1)).deleteById(anyString());
	}

	@Test
	public void testFindById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(this.recipeRepository.findById(anyString())).thenReturn(recipeOptional);

		RecipeCommand foundRecipe = this.recipeService.findById(RECIPE_ID);

		assertNotNull(foundRecipe, "Null recipe found");
		assertEquals(recipe.getId(), foundRecipe.getId());
		verify(this.recipeRepository, times(1)).findById(anyString());
		verifyNoMoreInteractions(this.recipeRepository);
		verify(this.recipeRepository, never()).findAll();
	}
	
	@Test
	public void testFindByIdNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
		
		when(this.recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		assertThrows(NotFoundException.class, () -> this.recipeService.findById(RECIPE_ID));
		verify(this.recipeRepository, times(1)).findById(anyString());
		verifyNoMoreInteractions(this.recipeRepository);
	}

	@Test
	public void testFindAll() throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		when(this.recipeRepository.findAll()).thenReturn(recipesData);

		Set<RecipeCommand> recipes = recipeService.findAll();

		assertEquals(recipes.size(), 1);
		verify(this.recipeRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.recipeRepository);
		verify(this.recipeRepository, never()).findById(anyString());
	}
}