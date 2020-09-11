/**
 * 
 */
package com.twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.commands.NotesCommand;
import com.twolak.springframework.domain.Notes;

/**
 * @author twolak
 *
 */
class NotesToNotesCommandTest {

	private static final String DESCRIPTION = "Note description";
	private static final String ID_VALUE = "n1";

	private NotesToNotesCommand notesToNotesCommand;

	@BeforeEach
	void setUp() throws Exception {
		this.notesToNotesCommand = new NotesToNotesCommand();
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.notesToNotesCommand.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.notesToNotesCommand.convert(new Notes()));
	}

	@Test
	void testConvert() throws Exception {
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setNote(DESCRIPTION);

		NotesCommand notesCommand = this.notesToNotesCommand.convert(notes);

		assertNotNull(notesCommand);
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(DESCRIPTION, notesCommand.getNote());
	}

}
