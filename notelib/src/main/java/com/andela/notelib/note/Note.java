package com.andela.notelib.note;


import android.os.Parcelable;

public abstract class Note implements Parcelable, Comparable<Note> {
    private int id;
    private int folderId;
    private int trash;
    private long date;

    public Note() {
        this.date = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getTrash() {
        return trash;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

    @Override
    public int compareTo(Note another) {
        return this.getId() > another.getId() ? -1 : 1;
    }
}
