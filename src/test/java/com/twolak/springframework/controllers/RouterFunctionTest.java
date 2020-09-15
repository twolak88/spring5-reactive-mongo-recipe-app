/**
 * 
 */
package com.twolak.springframework.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.config.WebConfig;
import com.twolak.springframework.services.RecipeService;

import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
public class RouterFunctionTest {
	
	private WebTestClient webTestClient;
	
	@Mock
	private RecipeService recipeService;
	
	@BeforeEach
	public void setUp() {
		RouterFunction<?> routerFunction = new WebConfig().routes(recipeService);
		this.webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
	}
	
	@Test
	public void testFindAll() {
		
		when(this.recipeService.findAll()).thenReturn(Flux.just());
		
		this.webTestClient.get().uri("/api/recipes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();
	}
	
	@Test
	public void testFindAllWithData() {
		
		when(this.recipeService.findAll()).thenReturn(Flux.just(new RecipeCommand(), new RecipeCommand(), new RecipeCommand()));
		
		this.webTestClient.get().uri("/api/recipes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(RecipeCommand.class);
	}
}
