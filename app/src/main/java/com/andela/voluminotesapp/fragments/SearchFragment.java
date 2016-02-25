package com.andela.voluminotesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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

        hideNoFeed();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchFilter = new SearchFilter(getContext(), notesRecyclerAdapter);
    }

    public void onQueryTextChange(CharSequence sequence) {
        if (searchFilter != null)
            searchFilter.filter(sequence);
    }
}