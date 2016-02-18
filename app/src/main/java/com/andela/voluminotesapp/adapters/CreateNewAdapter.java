package com.andela.voluminotesapp.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andela.notelib.note.Folders;
import com.andela.voluminotesapp.fragments.PaperNoteFragment;


public class CreateNewAdapter extends FragmentPagerAdapter {
    private int type;
    public CreateNewAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (this.type) {
            case Folders.STATIC_PAPER_NOTE:
                fragment = new PaperNoteFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Folders.STATIC_SINGLE_NOTE;
    }
}
