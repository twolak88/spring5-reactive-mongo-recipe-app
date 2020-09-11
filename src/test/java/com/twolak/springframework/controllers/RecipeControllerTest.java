package com.twolak.springframework.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
	private final int METHODS_CALL_TIMES = 1;
	
	private final String ID = "a1";

	@Mock
	private RecipeService recipeService;

	@InjectMocks
	private RecipeController recipeController;

	@Mock
	private Model model;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.recipeController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	public void testGetRecipeById() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID);

		when(this.recipeService.findById(anyString())).thenReturn(recipeCommand);

		this.mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.hasProperty("id", is(ID))))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("getRecipe"));

		verify(this.recipeService, times(METHODS_CALL_TIMES)).findById(anyString());
		verifyNoMoreInteractions(this.recipeService);
	}
	
	@Test
	public void testGetRecipeByIdNotFound() throws Exception {
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
        		.andExpect(view().name("recipe/error/404error"));
		verify(this.recipeService, times(METHODS_CALL_TIMES)).findById(anyString());
		verifyNoMoreInteractions(this.recipeService);
	}
	
//	@Test
//	public void testGetRecipeByIdNumberFormatEx() throws Exception {
//        mockMvc.perform(get("/recipe/abc/show"))
//                .andExpect(status().isBadRequest())
//        		.andExpect(view().name("recipe/error/404error"));
//	}

	@Test
	public void testNewRecipeForm() throws Exception {
		this.mockMvc.perform(get("/recipe/new")).andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.notNullValue(RecipeCommand.class)))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("newRecipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID);

		when(this.recipeService.save(any())).thenReturn(recipeCommand);

		this.mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("recipeId", "")
				.param("description", "some description")
				.param("directions", "some directions")
				.param("prepTime", "5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/" + ID + "/show"));
	}
	
	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);
        
//        when(this.recipeService.save(any())).thenReturn(command);
        
		this.mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("cookTime", "3000"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("recipe", Matchers.notNullValue(RecipeCommand.class)))
				.andExpect(view().name("recipe/recipeform"));
	}

	@Test
	public void testUpdateRecipeForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID);

		when(this.recipeService.findById(anyString())).thenReturn(recipeCommand);

		this.mockMvc.perform(get("/recipe/" + ID + "/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.notNullValue(RecipeCommand.class)))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("updateRecipe"));
		verify(this.recipeService, times(METHODS_CALL_TIMES)).findById(anyString());
		verifyNoMoreInteractions(this.recipeService);
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		
		verify(this.recipeService, times(1)).deleteById(anyString());
		verifyNoMoreInteractions(this.recipeService);
	}
}
