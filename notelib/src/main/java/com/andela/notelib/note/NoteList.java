package com.andela.notelib.note;


import java.util.ArrayList;
import java.util.Collection;

public class NoteList<T extends Note> extends ArrayList<T> {

    public NoteList() {
        super();
    }

    public T getNote(int index) {
        for (T t : this) {
            if (t.getId() == index)
                return t;
        }
        return null;
    }

    public NoteList<T> getFolderNotes(int index) {
        NoteList<T> ts = new NoteList<>();
        for (T t : this) {
            if (t.getFolderId() == index)
                ts.add(t);
        }
        return ts;
    }

    public void updateNote(T element) {
        int oldIndex = 0;
        for (T t : this) {
            if (t.getId() == element.getId()) {
                this.set(oldIndex, element);
                break;
            }
            oldIndex++;
        }
    }

    public boolean containsNote(T element) {
        for (T t : this) {
            if (t.getId() == element.getId())
                return true;
        }
        return false;
    }

    public boolean addNote(T t) {
        return this.add(t);
    }

    public void addOrderedNote(T t) {
        this.add(0, t);
    }

    public boolean removeNote(T element) {
        for (T t : this) {
            if (t.getId() == element.getId())
                return remove(t);
        }
        return false;
    }

    public boolean containsAllNote(Collection<T> elements) {
        boolean flag = false;
        for (T t : elements) {
            flag = containsNote(t);
        }
        return flag;
    }

    public boolean addAllNote(Collection<? extends T> c) {
        boolean flag = false;
        for (T t : c)
            flag = addNote(t);
        return flag;
    }

    public void addAllOrderedNote(Collection<? extends T> c) {
        for (T t : c)
            addOrderedNote(t);
    }

    public boolean removeAllNote(Collection<T> elements) {
        boolean flag = false;
        for (T t : elements) {
            flag = removeNote(t);
        }
        return flag;
    }
}
