/**
 * 
 */
package com.twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.twolak.springframework.commands.NotesCommand;
import com.twolak.springframework.domain.Notes;

import lombok.Synchronized;

/**
 * @author twolak
 *
 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (source == null) {
			return null;
		}
		final NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(source.getId());
		notesCommand.setNote(source.getNote());
		return notesCommand;
	}

}
