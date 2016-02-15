package com.andela.voluminotesapp.fragments;


import android.support.v7.widget.LinearLayoutManager;
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
        setHasOptionsMenu(true);
        notesRecyclerAdapter = new NotesRecyclerAdapter(
                getContext(),
                MyApplication.getNoteManager(getContext()).getAllTrashNotes(),
                R.layout.linear_item,
                NoteManager.TRASH);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trash_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.trashItem:
                deleteAllNotes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}