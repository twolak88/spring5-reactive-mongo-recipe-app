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
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.repositories.reactive.RecipeReactiveRepository;
import com.twolak.springframework.services.RecipeService;
import com.twolak.springframework.services.impl.RecipeServiceImpl;

import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = IndexController.class)
//@Import(RecipeServiceImpl.class)//not needed
public class IndexControllerTest {
	
//	private final int METHODS_CALL_TIMES = 1;
//	private final int RECIPES_IN_SET = 2;
	
//	@InjectMocks
//	private IndexController indexController;
	
//	@MockBean
//	private RecipeReactiveRepository recipeReactiveRepository; //not needed only service
	
	@MockBean
	private RecipeService recipeService;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@BeforeEach
	public void setUp() throws Exception {
//		this.webTestClient = WebTestClient.bindToController(indexController).build();//not needed annotation configuration
	}
	
	@Test
	public void testMVCGetIndexPage() throws Exception {
		
		when(recipeService.findAll()).thenReturn(Flux.just(new RecipeCommand()));
//		when(this.recipeReactiveRepository.findAll()).thenReturn(Flux.just(new Recipe()));
		
		this.webTestClient.get().uri("/").header(HttpHeaders.ACCEPT, "application/json")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectStatus().isOk()
				.expectBody();
		this.webTestClient.get().uri("/index/")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectStatus().isOk()
				.expectBody();
		this.webTestClient.get().uri("/indexpage/")
				.exchange()
				.expectStatus().is4xxClientError();
		
//		mockMvc.perform(get("/index/"))
//			.andExpect(status().isOk())
//			.andExpect(status().is2xxSuccessful())
//			.andExpect(view().name("index"));
//		mockMvc.perform(get("/"))
//			.andExpect(status().isOk())
//			.andExpect(status().is2xxSuccessful())
//			.andExpect(view().name("index"));
//		mockMvc.perform(get("/page"))
//			.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Disabled
	public void testGetIndexPage() throws Exception {
		
//		//given
//		Set<RecipeCommand> recipes = new HashSet<>();
//		for(int i = 0; i < RECIPES_IN_SET; i++) {
//			RecipeCommand recipe = new RecipeCommand();
//			recipe.setId("a" + i);
//			recipes.add(recipe);
//		}
//		
//		when(recipeService.findAll()).thenReturn(Flux.fromIterable(recipes));
//		
//		ArgumentCaptor<List<RecipeCommand>> argumentCaptor = ArgumentCaptor.forClass(List.class); 
//		
//		//when
//		String viewName = indexController.getIndexPage(model);
//		
//		//then
//		assertEquals("index", viewName);
//		verify(model, times(METHODS_CALL_TIMES)).addAttribute(eq("recipes"), argumentCaptor.capture());
//		verify(recipeService, times(METHODS_CALL_TIMES)).findAll();
//		
//		List<RecipeCommand> setInController = argumentCaptor.getValue();
//		assertEquals(RECIPES_IN_SET, setInController.size());
	}
}