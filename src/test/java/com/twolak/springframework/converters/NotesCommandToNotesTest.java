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
class NotesCommandToNotesTest {

	private static final String DESCRIPTION = "Note description";
	private static final String ID_VALUE = "n1";

	private NotesCommandToNotes notesCommandToNotes;

	@BeforeEach
	void setUp() throws Exception {
		notesCommandToNotes = new NotesCommandToNotes();
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.notesCommandToNotes.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.notesCommandToNotes.convert(new NotesCommand()));
	}

	@Test
	void testConvert() throws Exception {
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setNote(DESCRIPTION);

		Notes notes = this.notesCommandToNotes.convert(notesCommand);

		assertNotNull(notes);
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(DESCRIPTION, notes.getNote());
	}

}
