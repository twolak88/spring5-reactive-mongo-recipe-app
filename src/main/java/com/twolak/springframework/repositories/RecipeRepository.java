/**
 * 
 */
package com.twolak.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import com.twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
