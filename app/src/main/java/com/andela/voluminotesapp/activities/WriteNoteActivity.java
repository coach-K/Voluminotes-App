package com.andela.voluminotesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.CreateNewAdapter;
import com.andela.voluminotesapp.adapters.OpenExistingAdapter;
import com.andela.voluminotesapp.utilities.Launcher;
import com.andela.voluminotesapp.utilities.MsgBox;


public class WriteNoteActivity extends AppCompatActivity {
    private Context context = WriteNoteActivity.this;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        manageToolbar();

        viewPager = (ViewPager) findViewById(R.id.noteViewPager);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Launcher.TRANSPORT);

        int position = bundle.getInt(MainActivity.POSITION, Integer.MAX_VALUE);
        int type = bundle.getInt(MainActivity.TYPE, Integer.MAX_VALUE);

        if (position != Integer.MAX_VALUE) {
            openExistingNote(position);
        } else if (type != Integer.MAX_VALUE) {
            createNewNote(type);
        }
    }

    private void manageToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface alwaysInMyHeart = Typeface.createFromAsset(getAssets(), getString(R.string.heart_font));
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(alwaysInMyHeart);
        setSupportActionBar(toolbar);
    }

    private void createNewNote(int type) {
        CreateNewAdapter createNewAdapter = new CreateNewAdapter(getSupportFragmentManager(), type);
        viewPager.setAdapter(createNewAdapter);
        viewPager.setCurrentItem(type);
    }

    private void openExistingNote(int position) {
        OpenExistingAdapter openExistingAdapter = new OpenExistingAdapter(getSupportFragmentManager(), context);
        viewPager.setAdapter(openExistingAdapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        MsgBox.show(context, getString(R.string.canceled));
        super.onBackPressed();
    }
}
