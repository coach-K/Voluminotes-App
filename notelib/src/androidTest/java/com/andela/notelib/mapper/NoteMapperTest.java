package com.andela.notelib.mapper;

import android.test.ApplicationTestCase;

import com.andela.notelib.note.PaperNote;

import junit.framework.TestCase;

/**
 * Created by andela on 2/13/16.
 */
public class NoteMapperTest extends TestCase {

    PaperNote paperNote;
    NoteMapper<PaperNote> noteMapper;

    @Override
    protected void setUp() throws Exception {
        paperNote = new PaperNote("Everybody","Beautiful people", "Arial", 23, 0);
        noteMapper = new NoteMapper<>();
        super.setUp();
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testReadNote() throws Exception {
        String strPaper = noteMapper.readNote(paperNote);
        assertNotNull(strPaper);
    }

    public void testWriteNote() throws Exception {
        String strPaper = noteMapper.readNote(paperNote);
        PaperNote newPaperNote = noteMapper.writeNote(strPaper, PaperNote.class);
        assertEquals(newPaperNote.getNote(), paperNote.getNote());
    }

}