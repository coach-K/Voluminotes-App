package com.andela.voluminotesapp.activities;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.fragments.SettingsFragment;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        manageToolbar();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }

    private void manageToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface alwaysInMyHeart = Typeface.createFromAsset(getAssets(), getString(R.string.heart_font));
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(alwaysInMyHeart);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
