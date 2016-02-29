package com.andela.notelib.note;

import android.os.Parcel;

public class PaperNote extends Note {
    private String title;
    private String note;
    private String fontFamily;
    private int fontSize;
    private int background;

    public PaperNote() {
        super();
        this.title = "";
        this.note = "";
    }

    public PaperNote(String title, String note, String fontFamily, int fontSize, int background) {
        super();
        this.title = title;
        this.note = note;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    protected PaperNote(Parcel in) {
        setId(in.readInt());
        setFolderId(in.readInt());
        setTrash(in.readInt());
        setDate(in.readLong());
        title = in.readString();
        note = in.readString();
        fontFamily = in.readString();
        fontSize = in.readInt();
        background = in.readInt();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getFolderId());
        dest.writeInt(getTrash());
        dest.writeLong(getDate());
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(fontFamily);
        dest.writeInt(fontSize);
        dest.writeInt(background);
    }

    public static final Creator<PaperNote> CREATOR = new Creator<PaperNote>() {
        @Override
        public PaperNote createFromParcel(Parcel in) {
            return new PaperNote(in);
        }

        @Override
        public PaperNote[] newArray(int size) {
            return new PaperNote[size];
        }
    };
}
