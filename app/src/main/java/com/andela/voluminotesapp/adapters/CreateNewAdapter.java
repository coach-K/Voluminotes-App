package com.andela.voluminotesapp.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.andela.notelib.note.Folders;
import com.andela.voluminotesapp.fragments.AudioNoteFragment;
import com.andela.voluminotesapp.fragments.DrawNoteFragment;
import com.andela.voluminotesapp.fragments.PaperNoteFragment;
import com.andela.voluminotesapp.fragments.TodoNoteFragment;


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
            case Folders.STATIC_TODO_NOTE:
                fragment = new TodoNoteFragment();
                break;
            case Folders.STATIC_AUDIO_NOTE:
                fragment = new AudioNoteFragment();
                break;
            case Folders.STATIC_DRAW_NOTE:
                fragment = new DrawNoteFragment();
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
