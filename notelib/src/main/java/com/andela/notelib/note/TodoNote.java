package com.andela.notelib.note;


import android.os.Parcel;

import java.util.ArrayList;

public class TodoNote extends Note {
    private ArrayList<Todo> todos;

    public TodoNote() {
        super();
    }

    public TodoNote(ArrayList<Todo> todos) {
        super();
        this.todos = todos;
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<Todo> todos) {
        this.todos = todos;
    }

    protected TodoNote(Parcel in) {
        setId(in.readInt());
        setFolderId(in.readInt());
        setTrash(in.readInt());
        setDate(in.readLong());
        in.readTypedList(todos, Todo.CREATOR);
    }

    public static final Creator<TodoNote> CREATOR = new Creator<TodoNote>() {
        @Override
        public TodoNote createFromParcel(Parcel in) {
            return new TodoNote(in);
        }

        @Override
        public TodoNote[] newArray(int size) {
            return new TodoNote[size];
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
        dest.writeLong(getDate());
        dest.writeTypedList(todos);
    }
}
