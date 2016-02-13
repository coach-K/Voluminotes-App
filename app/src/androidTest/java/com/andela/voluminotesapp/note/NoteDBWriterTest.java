package com.andela.voluminotesapp.note;

import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteDBWriter;
import com.andela.notelib.note.NoteList;
import com.andela.notelib.note.PaperNote;

import junit.framework.TestCase;


/**
 * Created by andela on 2/13/16.
 */
public class NoteDBWriterTest extends TestCase {

    NoteDBWriter<Note> noteDBWriter;
    PaperNote paperNote;
    String content = "My dad is my a hero";

    @Override
    protected void setUp() throws Exception {
        noteDBWriter = new NoteDBWriter<>("helloTable");
        paperNote = new PaperNote("Everybody","Beautiful people", "Arial", 23, 0);
        paperNote.setId(0);
        paperNote.setFolderId(0);
        noteDBWriter.insertNote(paperNote);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInsertNote() throws Exception {
        String insert = "INSERT";
        paperNote.setTitle(insert);
        paperNote.setId(20);
        noteDBWriter.insertNote(paperNote);
        PaperNote note = (PaperNote) noteDBWriter.selectById(20);
        assertEquals(note.getTitle(), insert);
    }

    public void testSelectAllNote() throws Exception {
        NoteList<Note> note = noteDBWriter.selectAllNote(0);
        assertNotNull(note);
    }

    public void testSelectByFolderId() throws Exception {
        updateNote();
        NoteList<Note> note = noteDBWriter.selectByFolderId(1, 0);
        assertNotNull(note);
    }

    public void testSelectById() throws Exception {
        noteDBWriter.deleteAlNote(0);
        noteDBWriter.deleteAlNote(1);
        updateNote();
        noteDBWriter.insertNote(paperNote);
        PaperNote note = (PaperNote) noteDBWriter.selectById(4);
        assertEquals(note.getNote(), content);
    }

    private void updateNote(){
        String title = "Hero";
        paperNote.setTitle(title);
        paperNote.setNote(content);
        paperNote.setId(4);
        paperNote.setFolderId(0);
    }

    public void testUpdateNote() throws Exception {
        noteDBWriter.deleteAlNote(0);
        noteDBWriter.deleteAlNote(1);
        String update = "The boys are back";
        updateNote();
        noteDBWriter.insertNote(paperNote);
        paperNote.setNote(update);
        noteDBWriter.updateNote(paperNote);
        PaperNote note = (PaperNote) noteDBWriter.selectById(4);
        assertEquals(note.getNote(), update);
    }

    public void testDeleteNote() throws Exception {
        updateNote();

        noteDBWriter.insertNote(paperNote);
        assertNotNull(noteDBWriter.selectById(4));
        noteDBWriter.deleteNote(4);
        assertNull(noteDBWriter.selectById(4));
    }

    public void testDeleteFolderNotes() throws Exception {
        updateNote();
        noteDBWriter.insertNote(paperNote);
        noteDBWriter.deleteFolderNotes(0);
        assertEquals(noteDBWriter.selectByFolderId(0, 0).size(), 0);
    }

    public void testDeleteAllNote() throws Exception {
        noteDBWriter.deleteAlNote(0);
        noteDBWriter.deleteAlNote(1);
        assertEquals(noteDBWriter.selectAllNote(0).size(), 0);
        assertEquals(noteDBWriter.selectAllNote(1).size(), 0);
    }
}