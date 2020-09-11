/**
 * 
 */
package com.twolak.springframework.commands;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
	private String id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
}
