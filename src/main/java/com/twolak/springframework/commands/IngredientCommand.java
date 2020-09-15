/**
 * 
 */
package com.twolak.springframework.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class IngredientCommand {
	private String id;
	private String recipeId;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@NotNull
	@Min(1)
	@Max(99)
	private BigDecimal amount;
	
	@NotNull
	private UnitOfMeasureCommand unitOfMeasure;
}
