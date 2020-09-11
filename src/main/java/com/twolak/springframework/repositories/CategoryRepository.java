/**
 * 
 */
package com.twolak.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends CrudRepository<Category, String> {
	
	Optional<Category> findByDescription(String description);
}
