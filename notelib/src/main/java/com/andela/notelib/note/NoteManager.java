package com.andela.notelib.note;


import android.content.Context;

import com.andela.notelib.util.Settings;
import com.andela.notelib.util.SortById;

import java.util.Collections;

public class NoteManager {
    public static final int NOTES = 0;
    public static final int TRASH = 1;

    private String COUNT = "count";
    private int counter;
    private NoteDBWriter<Note> noteDBWriter;
    private NoteList<Note> notes = new NoteList<>();
    private NoteList<Note> trashNotes = new NoteList<>();
    private Context context;

    public NoteManager(Context context, String tableName) throws Exception {
        this.noteDBWriter = new NoteDBWriter<>(tableName);

        this.notes = this.noteDBWriter.selectAllNote(NOTES);
        Collections.sort(this.notes, new SortById());

        this.trashNotes = this.noteDBWriter.selectAllNote(TRASH);
        Collections.sort(this.trashNotes, new SortById());

        this.COUNT += tableName;
        this.context = context;
        counter = getCounter();
    }

    public Note saveNote(Note note) {
        note.setId(counter);
        note.setTrash(NOTES);
        this.notes.addOrderedNote(note);
        counter++;
        return note;
    }

    public void updateNote(Note note) {
        this.notes.updateNote(note);
    }

    public boolean containsNote(Note note) {
        return this.notes.containsNote(note);
    }

    public void moveNoteToTrash(Note note) {
        this.notes.removeNote(note);
        note.setTrash(TRASH);
        this.trashNotes.addOrderedNote(note);
    }

    public void restoreNoteFromTrash(Note note) {
        this.trashNotes.removeNote(note);
        note.setTrash(NOTES);
        this.notes.addOrderedNote(note);
    }

    public void deleteFromTrash(Note note) {
        this.trashNotes.removeNote(note);
        this.noteDBWriter.deleteNote(note.getId());
    }

    public void deleteNote(Note note) {
        this.trashNotes.removeNote(note);
        this.notes.removeNote(note);
        this.noteDBWriter.deleteNote(note.getId());
    }

    public void deleteAllTrash() {
        this.trashNotes.clear();
        this.noteDBWriter.deleteAlNote(TRASH);
    }

    public void deleteAll() {
        this.notes.clear();
        this.trashNotes.clear();
        this.noteDBWriter.deleteAlNote(NOTES);
        this.noteDBWriter.deleteAlNote(TRASH);
    }

    public int getNotePosition(Note note) {
        return this.notes.indexOf(note);
    }

    public void saveChanges() {
        saveCounter(counter);
        makeChangesToDatabase(this.notes);
        makeChangesToDatabase(this.trashNotes);
    }

    private void makeChangesToDatabase(NoteList<Note> noteList) {
        if (noteList.size() > 0) {
            for (Note note : noteList) {
                if (this.noteDBWriter.selectById(note.getId()) == null) {
                    this.noteDBWriter.insertNote(note);
                } else {
                    this.noteDBWriter.updateNote(note);
                }
            }
        }
    }

    public NoteList<Note> getAllNotes() {
        return this.notes;
    }

    public NoteList<Note> getAllTrashNotes() {
        return this.trashNotes;
    }

    public Note getNote(Note note) {
        return this.notes.getNote(note.getId());
    }

    public Note getNoteById(int id) {
        return this.notes.getNote(id);
    }

    public Note getTrashNote(Note note) {
        return this.trashNotes.getNote(note.getId());
    }

    public NoteList<Note> getFolderNotes(int index) {
        return this.notes.getFolderNotes(index);
    }

    public int getNotesSize() {
        return this.notes.size();
    }

    public int getTrashNotesSize() {
        return this.trashNotes.size();
    }

    public boolean isNotesEmpty() {
        return this.getNotesSize() == 0;
    }

    public boolean isTrashNotesEmpty() {
        return this.getTrashNotesSize() == 0;
    }

    private void saveCounter(int counter) {
        Settings.saveInt(context, COUNT, counter);
    }

    private int getCounter() {
        return Settings.fetchInt(context, COUNT, 0);
    }
}
