package com.andela.notelib.note;


import android.os.Parcel;

public class DefaultNote extends Note {
    private String defaultNote;

    public DefaultNote() {
    }

    protected DefaultNote(Parcel in) {
        setId(in.readInt());
        setFolderId(in.readInt());
        setTrash(in.readInt());
        defaultNote = in.readString();
    }

    public static final Creator<DefaultNote> CREATOR = new Creator<DefaultNote>() {
        @Override
        public DefaultNote createFromParcel(Parcel in) {
            return new DefaultNote(in);
        }

        @Override
        public DefaultNote[] newArray(int size) {
            return new DefaultNote[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getFolderId());
        dest.writeInt(getTrash());
        dest.writeString(defaultNote);
    }

    public String getDefaultNote() {
        return defaultNote;
    }

    public void setDefaultNote(String defaultNote) {
        this.defaultNote = defaultNote;
    }
}
