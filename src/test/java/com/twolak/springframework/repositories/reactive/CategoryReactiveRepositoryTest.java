/**
 * 
 */
package com.twolak.springframework.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
	
	private static final String CAT_DESC = "desc1";
	@Autowired
	private CategoryReactiveRepository categoryReactiveRepository;
	
	@BeforeEach
	public void setUp() {
		this.categoryReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testCategorySave() {
		Category category = new Category();
		category.setDescription(CAT_DESC);
		
		this.categoryReactiveRepository.save(category).block();
		
		assertEquals(1L, this.categoryReactiveRepository.count().block());
	}
	
	@Test
	public void testFindByDescription() {
		Category category = new Category();
		category.setDescription(CAT_DESC);
		
		this.categoryReactiveRepository.save(category).block();
		
		Category foundCat = this.categoryReactiveRepository.findByDescription(CAT_DESC).block();
		
		assertNotNull(foundCat.getId());
		assertEquals(CAT_DESC, foundCat.getDescription());
	}
}
