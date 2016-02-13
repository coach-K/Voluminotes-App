package com.andela.notelib.note;


import com.andela.notelib.R;

public enum Folders {
    GENERAL_NOTE(R.string.general_note);

    private final int folder;
    public static final int STATIC_PAPER_NOTE = 0;
    public static final int STATIC_TODO_NOTE = 1;
    public static final int STATIC_AUDIO_NOTE = 2;
    public static final int STATIC_DRAW_NOTE = 3;
    public static final int STATIC_GENERAL_NOTE = 4;

    Folders(int folder){
        this.folder = folder;
    }

    public int getFolder() {
        return folder;
    }
}
