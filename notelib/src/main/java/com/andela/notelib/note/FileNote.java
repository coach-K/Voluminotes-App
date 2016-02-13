package com.andela.notelib.note;


import android.os.Parcel;

public class FileNote extends Note {
    private String url;
    private String title;

    public FileNote() {
        super();
    }

    public FileNote(String url, String title) {
        super();
        this.url = url;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected FileNote(Parcel in) {
        setId(in.readInt());
        setFolderId(in.readInt());
        setTrash(in.readInt());
        setDate(in.readLong());
        url = in.readString();
        title = in.readString();
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
        dest.writeString(url);
        dest.writeString(title);
    }

    public static final Creator<FileNote> CREATOR = new Creator<FileNote>() {
        @Override
        public FileNote createFromParcel(Parcel in) {
            return new FileNote(in);
        }

        @Override
        public FileNote[] newArray(int size) {
            return new FileNote[size];
        }
    };
}
