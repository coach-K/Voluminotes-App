package com.andela.voluminotesapp.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andela.notelib.note.Folders;
import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteList;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.fragments.PaperNoteFragment;


public class OpenExistingAdapter extends FragmentPagerAdapter {
    private NoteList<Note> mItems;
    private Context context;

    public OpenExistingAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mItems = MyApplication.getNoteManager(this.context).getAllNotes();
    }

    @Override
    public Fragment getItem(int position) {
        return launchFragment(mItems.get(position));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    private Fragment launchFragment(Note note) {
        switch (note.getFolderId()) {
            case Folders.STATIC_PAPER_NOTE:
                return bundleFragment(note, new PaperNoteFragment());
            default:
                return null;
        }
    }

    private Fragment bundleFragment(Note note, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(context.getString(Folders.GENERAL_NOTE.getFolder()), note);
        fragment.setArguments(bundle);
        return fragment;
    }
}
