/**
 * 
 */
package com.twolak.springframework.commands;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.twolak.springframework.domain.Difficulty;

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
public class RecipeCommand {
	private String id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@NotNull
	@Min(1)
	@Max(999)
	private Integer prepTime;
	
	@Min(1)
	@Max(999)
	private Integer cookTime;
	
	@Min(1)
	@Max(100)
	private Integer servings;
	private String source;
	
	@URL
	private String url;
	
	@NotBlank
	private String directions;
	private Difficulty difficulty;
	
	@Valid
	private NotesCommand notes;
    private Byte[] image;
	private List<IngredientCommand> ingredients = new ArrayList<>();
	private List<CategoryCommand> categories = new ArrayList<>();
}
