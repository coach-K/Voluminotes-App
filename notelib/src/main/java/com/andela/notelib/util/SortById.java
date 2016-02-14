package com.andela.notelib.util;

import com.andela.notelib.note.Note;

import java.util.Comparator;

/**
 * Created by andela on 2/14/16.
 */
public class SortById implements Comparator<Note> {

    public int compare(Note one, Note another) {
        int returnVal = 0;

        if (one.getId() < another.getId()) {
            returnVal = -1;
        } else if (one.getId() > another.getId()) {
            returnVal = 1;
        } else if (one.getId() == another.getId()) {
            returnVal = 0;
        }
        return returnVal;
    }
}
