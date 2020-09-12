package com.twolak.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.twolak.springframework.domain.Category;

import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String>{
	Mono<Category> findByDescription(String description);
}
