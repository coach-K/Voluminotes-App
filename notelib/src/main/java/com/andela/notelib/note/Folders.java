package com.andela.notelib.note;


import com.andela.notelib.R;

public enum Folders {
    ID("id"),
    FOLDERID("folderid"),
    TRASH("trash"),
    NOTEBLOCK("noteblock"),
    GENERAL_NOTE(R.string.general_note);

    private int folder;
    private String columns;

    public static final int STATIC_PAPER_NOTE = 0;
    public static final int STATIC_TODO_NOTE = 1;
    public static final int STATIC_AUDIO_NOTE = 2;
    public static final int STATIC_DRAW_NOTE = 3;
    public static final int STATIC_GENERAL_NOTE = 4;
    public static final int STATIC_SINGLE_NOTE = 1;

    Folders(int folder){
        this.folder = folder;
    }
    Folders(String columns){
        this.columns = columns;
    }

    public int getFolder() {
        return folder;
    }

    public String getColumns() {
        return this.columns;
    }
}
