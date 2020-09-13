package com.twolak.springframework.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.stream.Collectors;

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
import com.twolak.springframework.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public class RecipeServiceImplTest {

	private final String RECIPE_ID = "r1";

	private RecipeServiceImpl recipeService;

	@Mock
	private RecipeReactiveRepository recipeReactiveRepository;

	private RecipeToRecipeCommand recipeToRecipeCommand;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeToRecipeCommand = new RecipeToRecipeCommand(
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new CategoryToCategoryCommand(), new NotesToNotesCommand());
		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeToRecipeCommand, null);

	}

	@Test
	public void testDeleteById() {
		String idToDelete = RECIPE_ID;
		this.recipeService.deleteById(idToDelete);

		// woid, returned nothing, so no when

		verify(this.recipeReactiveRepository, times(1)).deleteById(anyString());
	}

	@Test
	public void testFindById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);

		when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		RecipeCommand foundRecipe = this.recipeService.findById(RECIPE_ID).block();

		assertNotNull(foundRecipe, "Null recipe found");
		assertEquals(recipe.getId(), foundRecipe.getId());
		verify(this.recipeReactiveRepository, times(1)).findById(anyString());
		verifyNoMoreInteractions(this.recipeReactiveRepository);
		verify(this.recipeReactiveRepository, never()).findAll();
	}
	
//	@Test
//	public void testFindByIdNotFound() {
//		when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.empty());
//		
//		assertThrows(NotFoundException.class, () -> this.recipeService.findById(RECIPE_ID));
//		verify(this.recipeReactiveRepository, times(1)).findById(anyString());
//		verifyNoMoreInteractions(this.recipeReactiveRepository);
//	}

	@Test
	public void testFindAll() throws Exception {
		Recipe recipe = new Recipe();

		when(this.recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));

		Set<RecipeCommand> recipes = recipeService.findAll().collect(Collectors.toSet()).block();

		assertEquals(recipes.size(), 1);
		verify(this.recipeReactiveRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.recipeReactiveRepository);
		verify(this.recipeReactiveRepository, never()).findById(anyString());
	}
}