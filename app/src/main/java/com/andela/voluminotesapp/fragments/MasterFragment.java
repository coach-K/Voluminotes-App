package com.andela.voluminotesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;
import com.andela.voluminotesapp.callbacks.FragmentRecyclerListener;
import com.andela.voluminotesapp.callbacks.SimpleItemTouchHelperCallback;


public abstract class MasterFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected NotesRecyclerAdapter notesRecyclerAdapter;

    private TextView noFeed;
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

        initializeComponents(view);

        hideNoFeed();

        onFragmentViewCreated();

        notesRecyclerAdapter.setFragmentRecyclerListener(this.fragmentRecyclerListener);
        recyclerView.setAdapter(notesRecyclerAdapter);
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper.Callback itemTouchHelperCallback =
                new SimpleItemTouchHelperCallback(notesRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initializeComponents(View view) {
        noFeed = (TextView) view.findViewById(R.id.noFeed);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void restoreNote(int position) {
        notesRecyclerAdapter.setRestore(true);
        notesRecyclerAdapter.onItemDismiss(position);
    }

    public void deleteNote(int position) {
        notesRecyclerAdapter.setRestore(false);
        notesRecyclerAdapter.onItemDismiss(position);
    }

    public void hideNoFeed() {
        noFeed.setText("");
        noFeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    public void showNoFeed(String title, int icon) {
        noFeed.setText(title);
        noFeed.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
    }

    public void emptyTrash() {
        notesRecyclerAdapter.deleteAllNotes();
    }

    public void setFragmentRecyclerListener(FragmentRecyclerListener fragmentRecyclerListener) {
        this.fragmentRecyclerListener = fragmentRecyclerListener;
    }

    public NotesRecyclerAdapter getNotesRecyclerAdapter() {
        return notesRecyclerAdapter;
    }

    public abstract void onFragmentViewCreated();
}
