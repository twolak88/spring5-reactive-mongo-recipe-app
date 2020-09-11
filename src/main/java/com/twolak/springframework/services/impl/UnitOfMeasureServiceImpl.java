/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.twolak.springframework.repositories.UnitOfMeasureRepository;
import com.twolak.springframework.services.UnitOfMeasureService;

/**
 * @author twolak
 *
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> findAll() {
//		Set<UnitOfMeasureCommand> uoms = new HashSet<>();
//		this.unitOfMeasureRepository.findAll().iterator().forEachRemaining(uom->uoms.add(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));
//		return uoms;
		return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(), false)
				.map(this.unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}
