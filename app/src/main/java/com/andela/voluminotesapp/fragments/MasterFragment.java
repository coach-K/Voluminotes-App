package com.andela.voluminotesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;
import com.andela.voluminotesapp.callbacks.FragmentRecyclerListener;
import com.andela.voluminotesapp.callbacks.SimpleItemTouchHelperCallback;


public abstract class MasterFragment extends Fragment {
    public RecyclerView recyclerView;
    public NotesRecyclerAdapter notesRecyclerAdapter;
    private FragmentRecyclerListener fragmentRecyclerListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        return inflater.inflate(R.layout.fragment_notelist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        onFragmentViewCreated();

        notesRecyclerAdapter.setFragmentRecyclerListener(this.fragmentRecyclerListener);
        recyclerView.setAdapter(notesRecyclerAdapter);
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper.Callback itemTouchHelperCallback = new SimpleItemTouchHelperCallback(notesRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void restoreNote(int position) {
        notesRecyclerAdapter.setRestore(true);
        notesRecyclerAdapter.onItemDismiss(position);
    }

    public void deleteNote(int position) {
        notesRecyclerAdapter.setRestore(false);
        notesRecyclerAdapter.onItemDismiss(position);
    }

    public void deleteAllNotes() {
        notesRecyclerAdapter.deleteAllNotes();
    }

    public void setFragmentRecyclerListener(FragmentRecyclerListener fragmentRecyclerListener) {
        this.fragmentRecyclerListener = fragmentRecyclerListener;
    }

    public abstract void onFragmentViewCreated();
}
