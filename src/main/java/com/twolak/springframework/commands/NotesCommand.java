/**
 * 
 */
package com.twolak.springframework.commands;

import javax.validation.constraints.NotBlank;

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
public class NotesCommand {
	private String id;
	
	@NotBlank
	private String note;
}
