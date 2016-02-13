package com.andela.notelib.mapper;


import com.andela.notelib.note.Note;
import com.google.gson.Gson;

public class NoteMapper<T extends Note> {

    public NoteMapper() {
    }

    public String readNote(Note note) {
        return new Gson().toJson(note);
    }

    public T writeNote(String jsonNote, Class<T> type) {
        return new Gson().fromJson(jsonNote, type);
    }
}
