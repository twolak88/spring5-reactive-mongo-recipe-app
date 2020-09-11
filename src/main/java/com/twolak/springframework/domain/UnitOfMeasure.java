/**
 * 
 */
package com.twolak.springframework.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@Document
public class UnitOfMeasure {
	
	@Id
	private String id;
	private String description;
}
