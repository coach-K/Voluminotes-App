package com.andela.voluminotesapp.fragments;

import android.support.v7.widget.LinearLayoutManager;

import com.andela.notelib.note.NoteList;
import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;
import com.andela.voluminotesapp.utilities.SearchFilter;


public class SearchFragment extends MasterFragment {

    private SearchFilter searchFilter;

    @Override
    public void onFragmentViewCreated() {
        notesRecyclerAdapter = new NotesRecyclerAdapter(
                getContext(),
                new NoteList<>(),
                R.layout.linear_item,
                NoteManager.NOTES);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchFilter = new SearchFilter(getContext(), notesRecyclerAdapter);
    }

    public void onQueryTextChange(CharSequence sequence) {
        if (searchFilter != null)
            searchFilter.filter(sequence);
    }
}