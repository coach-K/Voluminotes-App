package com.andela.voluminotesapp.callbacks;


import android.support.v7.widget.RecyclerView;

import com.andela.notelib.note.Note;

public interface FragmentRecyclerListener {
    void onViewDrag(RecyclerView.ViewHolder viewHolder, final int fromPosition);

    void onNoteDelete(int type, boolean restore, Note note);

    void onNoteLongClick(int type, int position);

    void onNoteClick(int type, int position, Note note);
}
