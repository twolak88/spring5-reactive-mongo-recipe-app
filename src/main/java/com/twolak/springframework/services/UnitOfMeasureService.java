package com.twolak.springframework.services;

import com.twolak.springframework.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
public interface UnitOfMeasureService {
	Flux<UnitOfMeasureCommand> findAll();
}
