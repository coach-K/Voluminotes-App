package com.andela.voluminotesapp.fragments;


import android.support.v7.widget.GridLayoutManager;

import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;


public class NoteGridFragment extends MasterFragment {
    @Override
    public void onFragmentViewCreated() {
        notesRecyclerAdapter = new NotesRecyclerAdapter(
                getContext(),
                MyApplication.getNoteManager(getContext()).getAllNotes(),
                R.layout.grid_item,
                NoteManager.NOTES);

        if (MyApplication.getNoteManager(getContext()).isNotesEmpty())
            showNoFeed(getActivity().getString(R.string.notdisplay), R.mipmap.pencil_dark);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}
