/**
 * 
 */
package com.twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.commands.CategoryCommand;
import com.twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
class CategoryToCategoryCommandTest {

	public static final String ID = "c1";
	public static final String DESCRIPTION = "description";
	
	private CategoryToCategoryCommand categoryToCategoryCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		this.categoryToCategoryCommand = new CategoryToCategoryCommand();
	}
	
	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.categoryToCategoryCommand.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.categoryToCategoryCommand.convert(new Category()));
	}
	
	@Test
	void testConvert() throws Exception {
		Category category = new Category();
		category.setId(ID);
		category.setDescription(DESCRIPTION);
		
		CategoryCommand categoryCommand = this.categoryToCategoryCommand.convert(category);
		
		assertNotNull(categoryCommand);
		assertEquals(ID, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
	}

}
