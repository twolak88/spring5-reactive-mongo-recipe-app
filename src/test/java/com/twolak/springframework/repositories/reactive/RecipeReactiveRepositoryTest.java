/**
 * 
 */
package com.twolak.springframework.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
	
	@Autowired
	private RecipeReactiveRepository recipeReactiveRepository;
	
	@BeforeEach
	public void setUp() {
		this.recipeReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testRecipeSave() {
		Recipe recipe = new Recipe();
		recipe.setDescription("good food");
		
		this.recipeReactiveRepository.save(recipe).block();
		
		assertEquals(1L, this.recipeReactiveRepository.count().block());
	}
}
