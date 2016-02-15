package com.andela.voluminotesapp.fragments;


import android.support.v7.widget.LinearLayoutManager;

import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;


public class NoteListFragment extends MasterFragment {
    @Override
    public void onFragmentViewCreated() {
        notesRecyclerAdapter = new NotesRecyclerAdapter(
                getContext(),
                MyApplication.getNoteManager(getContext()).getAllNotes(),
                R.layout.linear_item,
                NoteManager.NOTES);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}