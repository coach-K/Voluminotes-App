package com.andela.voluminotesapp.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.utilities.ApplicationExit;
import com.andela.voluminotesapp.utilities.MsgBox;


public class MyApplication extends Application {

    private static NoteManager noteManager;

    public static NoteManager getNoteManager(Context context) {
        if (noteManager == null) {
            try {
                noteManager = new NoteManager(context, "mightyactivity");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return noteManager;
    }

    @Override
    public void onCreate() {
        startService(new Intent(getBaseContext(), ApplicationExit.class));
        super.onCreate();
    }
}
