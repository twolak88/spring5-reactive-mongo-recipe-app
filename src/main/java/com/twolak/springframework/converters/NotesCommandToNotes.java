/**
 * 
 */
package com.twolak.springframework.converters;

import org.apache.commons.lang3.StringUtils;
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
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Synchronized
	@Nullable
	@Override
	public Notes convert(NotesCommand source) {
		if (source == null) {
			return null;
		}
		final Notes notes = new Notes();
		if (StringUtils.isNotBlank(source.getId())) {
			notes.setId(source.getId());
		}
		notes.setNote(source.getNote());
		return notes;
	}

}
