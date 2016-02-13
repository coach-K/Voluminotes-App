package com.andela.notelib.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    public Settings() {
    }

    public static void saveInt(Context context, String KEY_STORE, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_STORE, value);
        editor.commit();
    }

    public static int fetchInt(Context context, String KEY_STORE, int defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int intValue = preferences.getInt(KEY_STORE, defaultValue);
        return (intValue < defaultValue) ? defaultValue : intValue;
    }
}
