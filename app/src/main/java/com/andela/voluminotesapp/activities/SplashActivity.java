package com.andela.voluminotesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andela.voluminotesapp.utilities.Launcher;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Launcher.launch(SplashActivity.this, MainActivity.class);
        finish();
    }
}
