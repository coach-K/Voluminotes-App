package com.andela.notelib.note;

import com.andela.notelib.util.SortById;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * Created by andela on 2/14/16.
 */
public class NoteListTest extends TestCase {

    NoteList<Note> notes;
    PaperNote paperNote;
    PaperNote paperNote1;
    PaperNote paperNote2;
    PaperNote paperNote3;
    PaperNote paperNote4;
    PaperNote paperNote5;
    PaperNote paperNote6;
    PaperNote paperNote7;
    PaperNote paperNote8;
    PaperNote paperNote9;
    PaperNote paperNote10;
    PaperNote paperNote11;
    PaperNote paperNote12;
    PaperNote paperNote13;
    PaperNote paperNote14;

    NoteList<Note> noteList;

    public void setUp() throws Exception {
        notes = new NoteList<>();
        paperNote = new PaperNote("REAL", "Husbands of hollywood", "Corsiva", 40, 1);
        paperNote.setId(0);
        paperNote.setFolderId(0);
        paperNote1 = new PaperNote("STRONG", "MEN of hollywood", "Corsiva", 40, 1);
        paperNote1.setId(1);
        paperNote1.setFolderId(0);
        paperNote2 = new PaperNote("DANGEROUS", "DUDES of hollywood", "Corsiva", 40, 1);
        paperNote2.setId(2);
        paperNote2.setFolderId(0);
        paperNote3 = new PaperNote("MEAN", "GIRLS of hollywood", "Corsiva", 40, 1);
        paperNote3.setId(3);
        paperNote3.setFolderId(0);
        paperNote4 = new PaperNote("COOL", "GUYZ of hollywood", "Corsiva", 40, 1);
        paperNote4.setId(4);
        paperNote4.setFolderId(0);
        paperNote5 = new PaperNote("VUN", "HELLO of hollywood", "Corsiva", 40, 1);
        paperNote5.setId(5);
        paperNote5.setFolderId(0);
        paperNote6 = new PaperNote("SIX", "HELLO of hollywood", "Corsiva", 40, 1);
        paperNote6.setId(6);
        paperNote6.setFolderId(0);
        paperNote7 = new PaperNote("SEVEN", "YES of hollywood", "Corsiva", 40, 1);
        paperNote7.setId(7);
        paperNote7.setFolderId(0);
        paperNote8 = new PaperNote("EIGHT", "LOVE of hollywood", "Corsiva", 40, 1);
        paperNote8.setId(8);
        paperNote8.setFolderId(0);
        paperNote9 = new PaperNote("NINE", "CAN of hollywood", "Corsiva", 40, 1);
        paperNote9.setId(9);
        paperNote9.setFolderId(0);
        paperNote10 = new PaperNote("TEN", "DRINK of hollywood", "Corsiva", 40, 1);
        paperNote10.setId(10);
        paperNote10.setFolderId(0);
        paperNote11 = new PaperNote("ELEVEN", "MALT of hollywood", "Corsiva", 40, 1);
        paperNote11.setId(11);
        paperNote11.setFolderId(0);
        paperNote12 = new PaperNote("TWELVE", "COKE of hollywood", "Corsiva", 40, 1);
        paperNote12.setId(12);
        paperNote12.setFolderId(0);
        paperNote13 = new PaperNote("THIRTEEN", "MELLON of hollywood", "Corsiva", 40, 1);
        paperNote13.setId(13);
        paperNote13.setFolderId(0);
        paperNote14 = new PaperNote("FOURTEEN", "ORANGES of hollywood", "Corsiva", 40, 1);
        paperNote14.setId(14);
        paperNote14.setFolderId(0);

        noteList = new NoteList<>();
        noteList.addNote(paperNote1);
        noteList.addNote(paperNote);
        noteList.addNote(paperNote2);

        super.setUp();
    }

    private void addNote(){
        notes.addNote(paperNote);
        notes.addNote(paperNote1);
        notes.addNote(paperNote2);
    }

    private void addOrderedNote(){
        notes.addNote(paperNote2);
        notes.addNote(paperNote1);
        notes.addNote(paperNote);
        notes.addNote(paperNote3);
        notes.addNote(paperNote5);
        notes.addNote(paperNote4);
        notes.addNote(paperNote7);
        notes.addNote(paperNote6);
        notes.addNote(paperNote9);
        notes.addNote(paperNote8);
        notes.addNote(paperNote10);
        notes.addNote(paperNote13);
        notes.addNote(paperNote14);
        notes.addNote(paperNote12);
        notes.addNote(paperNote11);
    }

    public void tearDown() throws Exception {

    }

    public void testGetNote() throws Exception {
        addNote();
        PaperNote note = (PaperNote) notes.get(0);
        assertEquals(note.getNote(), paperNote.getNote());
    }

    public void testGetFolderNotes() throws Exception {
        addNote();
        NoteList<Note> noteList = notes.getFolderNotes(0);
        assertEquals(noteList, notes);
    }

    public void testUpdateNote() throws Exception {
        addNote();
        paperNote.setTitle("GET READY!");
        notes.updateNote(paperNote);
        assertEquals(((PaperNote) notes.getNote(0)).getTitle(), paperNote.getTitle());
    }

    public void testContainsNote() throws Exception {
        addNote();
        assertTrue(notes.containsNote(paperNote));
        notes.removeNote(paperNote);
        assertFalse(notes.containsNote(paperNote));
    }

    public void testAddNote() throws Exception {
        addNote();
        assertEquals(notes.size(), 3);
    }

    public void testAddOrderedNote() throws Exception {
        addOrderedNote();
        Collections.sort(notes, new SortById());
        assertEquals(((PaperNote) notes.get(14)).getTitle(), paperNote14.getTitle());
    }

    public void testRemoveNote() throws Exception {
        assertEquals(notes.size(), 0);
        addNote();
        assertEquals(notes.size(), 3);
        notes.removeNote(paperNote);
        assertEquals(notes.size(), 2);
    }

    public void testContainsAllNote() throws Exception {
        assertEquals(notes.size(), 0);
        addOrderedNote();
        assertEquals(notes.size(), 15);
        assertTrue(notes.containsAllNote(noteList));
    }

    public void testAddAllNote() throws Exception {
        assertEquals(notes.size(), 0);
        notes.addAllNote(noteList);
        assertEquals(notes.size(), 3);
    }

    public void testAddAllOrderedNote() throws Exception {
        assertEquals(notes.size(), 0);
        notes.addAllOrderedNote(noteList);
        assertEquals(((PaperNote)notes.get(0)).getTitle(), paperNote2.getTitle());
    }

    public void testRemoveAllNote() throws Exception {
        assertEquals(notes.size(), 0);
        notes.addAllOrderedNote(noteList);
        assertEquals(((PaperNote) notes.get(0)).getTitle(), paperNote2.getTitle());
        notes.removeAllNote(noteList);
        assertEquals(notes.size(), 0);
    }
}