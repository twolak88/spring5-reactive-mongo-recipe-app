/**
 * 
 */
package com.twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
class UnitOfMeasureCommandToUnitOfMeasureTest {

	private static final String DESCRIPTION = "Uom description";
	private static final String ID_VALUE = "u1";

	private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

	@BeforeEach
	void setUp() throws Exception {
		this.unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.unitOfMeasureCommandToUnitOfMeasure.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));
	}

	@Test
	void testConvert() throws Exception {
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(ID_VALUE);
		unitOfMeasureCommand.setDescription(DESCRIPTION);

		UnitOfMeasure unitOfMeasure = this.unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

		assertNotNull(unitOfMeasure);
		assertEquals(ID_VALUE, unitOfMeasure.getId());
		assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
	}
}
