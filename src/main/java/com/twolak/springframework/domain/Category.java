package com.twolak.springframework.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipes"})
@Document
public class Category {
	
	@Id
	private String id;
	private String description;
	private Set<Recipe> recipes;
}
