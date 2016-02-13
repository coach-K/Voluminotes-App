package com.andela.notelib.note;


import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {
    private String title;
    private long date;
    private boolean done;

    public Todo() {
    }

    public Todo(String title, long date) {
        this.title = title;
        this.date = date;
        this.done = false;
    }

    protected Todo(Parcel in) {
        title = in.readString();
        date = in.readLong();
        done = in.readByte() != 0;
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeLong(date);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
