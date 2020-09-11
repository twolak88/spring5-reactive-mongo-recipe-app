package com.twolak.springframework.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
public class CategoryTest {

	@InjectMocks
	Category category;
	
	@BeforeEach
	public void setUp() {
//		category = new Category();
	}
	
	@Test
	public void testGetId() throws Exception {
		String idVal = "c1";
		category.setId(idVal);
		
		assertEquals(idVal, category.getId());
	}
	
	@Test
	public void testGetDescription() throws Exception {
		String description = "description";
		category.setDescription(description);
		
		assertEquals(description, category.getDescription());
	}
	
	@Test
	public void testGetRecipes() throws Exception {
		Set<Recipe> recipes = new HashSet<>();
		Recipe recipe = new Recipe();
		recipe.setCookTime(1);
		recipe.setDescription("description");
		recipes.add(recipe);
		category.setRecipes(recipes);
		
		assertEquals(recipes.size(), category.getRecipes().size());
	}
}