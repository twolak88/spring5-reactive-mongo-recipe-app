package com.twolak.springframework.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {
	
	private final int METHODS_CALL_TIMES = 1;
	private final int RECIPES_IN_SET = 2;
	
	@InjectMocks
	private IndexController indexController;
	
	@Mock
	private RecipeService recipeService;
	
	@Mock
	private Model model;
	
	@BeforeEach
	public void setUp() throws Exception {
	}
	
	@Test
	public void testMVCGetIndexPage() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get("/index/"))
			.andExpect(status().isOk())
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("index"));
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("index"));
		mockMvc.perform(get("/page"))
			.andExpect(status().is4xxClientError());
	}
	
//	@Test
//	public void testGetIndexPage() throws Exception {
//		
//		//given
//		Set<RecipeCommand> recipes = new HashSet<>();
//		for(int i = 0; i < RECIPES_IN_SET; i++) {
//			RecipeCommand recipe = new RecipeCommand();
//			recipe.setId("a" + i);
//			recipes.add(recipe);
//		}
//		
//		when(recipeService.findAll()).thenReturn(recipes);
//		
//		ArgumentCaptor<Set<RecipeCommand>> argumentCaptor = ArgumentCaptor.forClass(Set.class); 
//		
//		//when
//		String viewName = indexController.getIndexPage(model);
//		
//		//then
//		assertEquals("index", viewName);
//		verify(model, times(METHODS_CALL_TIMES)).addAttribute(eq("recipes"), argumentCaptor.capture());
//		verify(recipeService, times(METHODS_CALL_TIMES)).findAll();
//		
//		Set<RecipeCommand> setInController = argumentCaptor.getValue();
//		assertEquals(RECIPES_IN_SET, setInController.size());
//	}
}