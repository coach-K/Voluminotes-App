package com.andela.voluminotesapp.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteList;
import com.andela.notelib.note.NoteManager;
import com.andela.notelib.note.PaperNote;
import com.andela.voluminotesapp.activities.MainActivity;
import com.andela.voluminotesapp.activities.SplashActivity;

import junit.framework.TestCase;

import org.junit.Rule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by andela on 2/13/16.
 */
public class NoteManagerTest  extends TestCase{

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private NoteManager noteManager;
    PaperNote paperNote;
    PaperNote note;

    @Override
    public void setUp() throws Exception {
        try {
            String noteTable = "notetaker";
            noteManager = new NoteManager(InstrumentationRegistry.getContext(), noteTable);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        this.noteManager.deleteAll();

        paperNote = new PaperNote("Wonderful", "This is a wonderful note", "Roboto", 30, 0);
        paperNote.setId(0);
        paperNote.setFolderId(0);

        note = new PaperNote("Spanish", "Do you speak spanish?", "Serif", 45, 0);
        note.setId(1);
        note.setFolderId(0);

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSaveNote() throws Exception {
        assertEquals(this.noteManager.getNotesSize(), 0);
        noteManager.saveNote(paperNote);
        assertEquals(this.noteManager.getNotesSize(), 1);
    }

    public void testUpdateNote() throws Exception {
        testSaveNote();
        String title = "Football";
        paperNote.setTitle(title);
        this.noteManager.updateNote(paperNote);

        PaperNote note = (PaperNote) this.noteManager.getNote(paperNote);
        assertEquals(note.getTitle(), title);
    }

    public void testContainsNote() throws Exception {
        testSaveNote();
        assertTrue(this.noteManager.containsNote(paperNote));
    }

    public void testMoveNoteToTrash() throws Exception {
        assertEquals(this.noteManager.getTrashNotesSize(), 0);
        assertEquals(this.noteManager.getNotesSize(), 0);
        testSaveNote();
        assertEquals(this.noteManager.getNotesSize(), 1);
        this.noteManager.moveNoteToTrash(paperNote);
        assertEquals(this.noteManager.getTrashNotesSize(), 1);
        assertEquals(this.noteManager.getNotesSize(), 0);
    }

    public void testRestoreNoteFromTrash() throws Exception {
        testMoveNoteToTrash();
        this.noteManager.restoreNoteFromTrash(paperNote);
        assertEquals(this.noteManager.getNotesSize(), 1);
    }

    public void testDeleteFromTrash() throws Exception {
        testMoveNoteToTrash();
        this.noteManager.deleteFromTrash(paperNote);
        assertEquals(this.noteManager.getTrashNotesSize(), 0);
    }

    public void testDeleteNote() throws Exception {
        testSaveNote();
        assertEquals(this.noteManager.getNotesSize(), 1);
        this.noteManager.deleteNote(paperNote);
        assertEquals(this.noteManager.getNotesSize(), 0);
    }

    public void testDeleteAllTrash() throws Exception {
        testMoveNoteToTrash();
        this.noteManager.deleteAllTrash();
        assertEquals(this.noteManager.getTrashNotesSize(), 0);
    }

    public void testDeleteAll() throws Exception {
        testSaveNote();
        this.noteManager.saveNote(note);
        assertEquals(this.noteManager.getNotesSize(), 2);
        this.noteManager.moveNoteToTrash(paperNote);
        assertEquals(this.noteManager.getNotesSize(), 1);
        assertEquals(this.noteManager.getTrashNotesSize(), 1);
        this.noteManager.deleteAll();
        assertEquals(this.noteManager.getNotesSize(), 0);
        assertEquals(this.noteManager.getTrashNotesSize(), 0);
    }

    public void testGetAllNotes() throws Exception {
        testSaveNote();
        this.noteManager.saveNote(note);
        assertEquals(this.noteManager.getNotesSize(), 2);

        NoteList<Note> notes = this.noteManager.getAllNotes();
        assertEquals(((PaperNote)notes.getNote(0)).getNote(), paperNote.getNote());
        assertEquals(((PaperNote)notes.getNote(1)).getNote(), note.getNote());
    }

    public void testGetAllTrashNotes() throws Exception {
        testSaveNote();
        this.noteManager.saveNote(note);
        assertEquals(this.noteManager.getNotesSize(), 2);

        this.noteManager.moveNoteToTrash(paperNote);
        this.noteManager.moveNoteToTrash(note);

        assertEquals(this.noteManager.getNotesSize(), 0);
        assertEquals(this.noteManager.getTrashNotesSize(), 2);

        NoteList<Note> notes = this.noteManager.getAllTrashNotes();
        assertEquals(((PaperNote)notes.getNote(0)).getNote(), paperNote.getNote());
        assertEquals(((PaperNote)notes.getNote(1)).getNote(), note.getNote());
    }

    public void testGetNote() throws Exception {
        assertEquals(this.noteManager.getNotesSize(), 0);
        testSaveNote();
        assertEquals(this.noteManager.getNotesSize(), 1);
        PaperNote note = (PaperNote) this.noteManager.getNote(paperNote);
        assertEquals(note.getNote(), paperNote.getNote());
    }

    public void testGetTrashNote() throws Exception {
        testMoveNoteToTrash();
        PaperNote note = (PaperNote) this.noteManager.getTrashNote(paperNote);
        assertEquals(note.getNote(), paperNote.getNote());
    }

    public void testGetFolderNotes() throws Exception {
        testSaveNote();
        NoteList<Note> notes = this.noteManager.getFolderNotes(0);
        assertEquals(((PaperNote) notes.getNote(0)).getNote(), paperNote.getNote());
    }

    public void testGetNotesSize() throws Exception {
        assertEquals(this.noteManager.getNotesSize(), 0);
        testSaveNote();
        assertEquals(this.noteManager.getNotesSize(), 1);
    }

    public void testGetTrashNotesSize() throws Exception {
        assertEquals(this.noteManager.getTrashNotesSize(), 0);
        testMoveNoteToTrash();
        assertEquals(this.noteManager.getTrashNotesSize(), 1);
    }

    public void testIsNotesEmpty() throws Exception {
        assertTrue(this.noteManager.isNotesEmpty());
        testSaveNote();
        assertFalse(this.noteManager.isNotesEmpty());
    }

    public void testIsTrashNotesEmpty() throws Exception {
        assertTrue(this.noteManager.isTrashNotesEmpty());
        testMoveNoteToTrash();
        assertFalse(this.noteManager.isTrashNotesEmpty());
    }
}