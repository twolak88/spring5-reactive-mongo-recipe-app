/**
 * 
 */
package com.twolak.springframework.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.services.IngredientService;
import com.twolak.springframework.services.RecipeService;
import com.twolak.springframework.services.UnitOfMeasureService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

	private static final String RECIP_ID = "r1";

	private static final String ING_ID = "i1";

	@Mock
	private RecipeService recipeService;

	@Mock
	private IngredientService ingredientService;

	@Mock
	private UnitOfMeasureService unitOfMeasureService;

	@InjectMocks
	private IngredientController ingredientController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findById(anyString())).thenReturn(recipeCommand);

		this.mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));
		verify(this.recipeService, times(1)).findById(anyString());
	}

	@Test
	public void testShowIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();

		when(this.ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);

		this.mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));
		verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyString(), anyString());
		verifyNoMoreInteractions(this.ingredientService);
	}
	
	@Test
	public void testShowIngredientNotFound() throws Exception {
		when(this.ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenThrow(NotFoundException.class);

		this.mockMvc.perform(get("/recipe/1/ingredient/1/show"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("recipe/error/404error"));
	}
	
//	@Test
//	public void testShowIngredientNumberFormatException() throws Exception {
//		this.mockMvc.perform(get("/recipe/1/ingredient/1b/show"))
//				.andExpect(status().isBadRequest())
//				.andExpect(view().name("recipe/error/404error"));
//	}
	
	@Test
	public void testNewIngredientForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ING_ID);
		
		when(this.recipeService.findById(anyString())).thenReturn(recipeCommand);
		when(this.unitOfMeasureService.findAll()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/1/ingredient/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
		verify(this.recipeService, times(1)).findById(anyString());
		verifyNoMoreInteractions(this.recipeService);
		verify(this.unitOfMeasureService, times(1)).findAll();
		verifyNoMoreInteractions(this.unitOfMeasureService);
	}

	@Test
	public void testUpdateIngredientForm() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();

		when(this.ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);

		this.mockMvc.perform(get("/recipe/1/ingredient/2/update")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model()
				.attributeExists("uomList"));
		verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyString(), anyString());
		verifyNoMoreInteractions(this.ingredientService);
		verify(this.unitOfMeasureService, times(1)).findAll();
		verifyNoMoreInteractions(this.unitOfMeasureService);
	}

	@Test
	public void testPostSaveOrUpdateIngredientForm() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ING_ID);
		ingredientCommand.setRecipeId(RECIP_ID);
		
		when(this.ingredientService.save(any())).thenReturn(ingredientCommand);
		
		this.mockMvc.perform(post("/recipe/" + RECIP_ID + "/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/" + RECIP_ID + "/ingredient/" + ING_ID + "/show"));
		verify(this.ingredientService, times(1)).save(any());
		verifyNoMoreInteractions(this.ingredientService);
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		this.mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));
		verify(this.ingredientService, times(1)).deleteById(anyString(), anyString());
		verifyNoMoreInteractions(this.ingredientService);
	}
}
