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

import com.twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
	
	private static final String TABLESPOON_UOM = "Tablespoon";
	@Autowired
	private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	@BeforeEach
	public void setUp() {
		this.unitOfMeasureReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testUomSave() {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(TABLESPOON_UOM);
		
		this.unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
		
		assertEquals(1L, this.unitOfMeasureReactiveRepository.count().block());
	}
	
	@Test
	public void testFindByDescription() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(TABLESPOON_UOM);
		
		this.unitOfMeasureReactiveRepository.save(uom).block();
		
		UnitOfMeasure foundUom = this.unitOfMeasureReactiveRepository.findByDescription(TABLESPOON_UOM).block();
		
		assertNotNull(foundUom.getId());
		assertEquals(TABLESPOON_UOM, foundUom.getDescription());
	}
}
