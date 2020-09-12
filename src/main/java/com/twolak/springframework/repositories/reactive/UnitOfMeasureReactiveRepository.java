package com.twolak.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.twolak.springframework.domain.UnitOfMeasure;

import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {
	Mono<UnitOfMeasure> findByDescription(String description);
}
