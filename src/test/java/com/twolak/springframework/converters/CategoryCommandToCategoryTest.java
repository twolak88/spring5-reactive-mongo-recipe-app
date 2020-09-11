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
class CategoryCommandToCategoryTest {

	public static final String ID_VALUE = "c1";
	public static final String DESCRIPTION = "description";

	private CategoryCommandToCategory categoryCommandToCategory;

	@BeforeEach
	public void setUp() throws Exception {
		this.categoryCommandToCategory = new CategoryCommandToCategory();
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.categoryCommandToCategory.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.categoryCommandToCategory.convert(new CategoryCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);

		Category category = this.categoryCommandToCategory.convert(categoryCommand);

		assertNotNull(category);
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}
}
