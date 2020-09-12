/**
 * 
 */
package com.twolak.springframework.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.twolak.springframework.domain.UnitOfMeasure;
import com.twolak.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
class UnitOfMeasureServiceImplTest {

	private UnitOfMeasureServiceImpl unitOfMeasureServiceImpl;

	@Mock
	private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.unitOfMeasureServiceImpl = new UnitOfMeasureServiceImpl(this.unitOfMeasureReactiveRepository,
				new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	void testFindAll() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("u1");
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("u2");
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom2.setId("u3");

		when(this.unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2, uom3));

		List<UnitOfMeasureCommand> uomCommands = this.unitOfMeasureServiceImpl.findAll().collectList().block();

		assertEquals(3, uomCommands.size());
		verify(this.unitOfMeasureReactiveRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.unitOfMeasureReactiveRepository);
	}

}
