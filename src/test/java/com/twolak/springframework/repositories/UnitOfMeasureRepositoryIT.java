package com.twolak.springframework.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.twolak.springframework.bootstrap.DataLoader;
import com.twolak.springframework.domain.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {
	
	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	private  CategoryRepository categoryRepository;
	
	@Autowired
	private  RecipeRepository recipeRepository;

	@BeforeEach
	public void setUp() throws Exception {
		DataLoader dataLoader = new DataLoader(unitOfMeasureRepository, categoryRepository, recipeRepository);
		
		dataLoader.onApplicationEvent(null);
	}
	
	@AfterEach
	private void clean() {
		if (unitOfMeasureRepository.count() > 0) {
			unitOfMeasureRepository.deleteAll();
		}
		if (categoryRepository.count() > 0) {
			categoryRepository.deleteAll();
		}
		if (recipeRepository.count() > 0) {
			recipeRepository.deleteAll();
		}
	}
	
	@Test
//	@DirtiesContext
	public void testFindByDescription() throws Exception {
		
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		
		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}
	
	@Test
	public void testFindByDescriptionCup() throws Exception {
		
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");
		
		assertEquals("Cup", uomOptional.get().getDescription());
	}
}