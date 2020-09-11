/**
 * 
 */
package com.twolak.springframework.domain;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
public class Notes {
	
	@Id
	private String id;
	private String note;
}
