/**
 * 
 */
package com.twolak.springframework.services.impl;

import org.springframework.stereotype.Service;

import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.twolak.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.twolak.springframework.services.UnitOfMeasureService;

import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Flux<UnitOfMeasureCommand> findAll() {
		return this.unitOfMeasureReactiveRepository.findAll().map(this.unitOfMeasureToUnitOfMeasureCommand::convert);
//		return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(), false)
//				.map(this.unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}
