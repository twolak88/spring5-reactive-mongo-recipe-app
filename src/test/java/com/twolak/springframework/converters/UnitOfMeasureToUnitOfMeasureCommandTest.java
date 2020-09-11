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
class UnitOfMeasureToUnitOfMeasureCommandTest {

	private static final String DESCRIPTION = "Uom description";
	private static final String ID_VALUE = "u1";

	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.unitOfMeasureToUnitOfMeasureCommand.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));
	}

	@Test
	void testConvert() throws Exception {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(ID_VALUE);
		unitOfMeasure.setDescription(DESCRIPTION);

		UnitOfMeasureCommand unitOfMeasureCommand = this.unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);

		assertNotNull(unitOfMeasureCommand);
		assertEquals(ID_VALUE, unitOfMeasureCommand.getId());
		assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
	}

}
