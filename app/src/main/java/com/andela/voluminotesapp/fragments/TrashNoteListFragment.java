package com.andela.voluminotesapp.fragments;


import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;


public class TrashNoteListFragment extends MasterFragment {
    @Override
    public void onFragmentViewCreated() {
        notesRecyclerAdapter = new NotesRecyclerAdapter(
                getContext(),
                MyApplication.getNoteManager(getContext()).getAllTrashNotes(),
                R.layout.linear_item,
                NoteManager.TRASH);

        if (MyApplication.getNoteManager(getContext()).isTrashNotesEmpty()) {
            showNoFeed(getActivity().getString(R.string.trashempty), R.mipmap.trash_dark);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}