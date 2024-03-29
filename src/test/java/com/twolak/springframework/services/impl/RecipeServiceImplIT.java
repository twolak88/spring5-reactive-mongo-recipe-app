/**
 * 
 */
package com.twolak.springframework.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.converters.RecipeToRecipeCommand;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.repositories.RecipeRepository;
import com.twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceImplIT {
	
	private static final String DESRIPTION = "some description"; 
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private RecipeToRecipeCommand recipeToRecipeCommand; 
	
//	@BeforeEach
//	public void setUp() throws Exception {
//		DataLoader dataLoader = new DataLoader(unitOfMeasureRepository, categoryRepository, recipeRepository);
//		
//		dataLoader.onApplicationEvent(null);
//	}
//	
//	@AfterEach
//	private void clean() {
//		unitOfMeasureRepository.deleteAll();
//		categoryRepository.deleteAll();
//		recipeRepository.deleteAll();
//	}
	
	
	@Test
	public void testSaveRecipes() throws Exception {
		Iterable<Recipe> recipes = this.recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = this.recipeToRecipeCommand.convert(testRecipe);
		
		testRecipeCommand.setDescription(DESRIPTION);
		RecipeCommand savedRecipeCommand = this.recipeService.save(testRecipeCommand).block();
		
		assertNotNull(savedRecipeCommand);
		assertEquals(DESRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(),savedRecipeCommand.getIngredients().size());
	}
}
