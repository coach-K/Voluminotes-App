package com.andela.voluminotesapp.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.andela.voluminotesapp.activities.MyApplication;

/**
 * Created by andela on 2/14/16.
 */
public class ApplicationExit extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        MyApplication.getNoteManager(getBaseContext()).saveChanges();
        super.onDestroy();
    }

    public void onTaskRemoved(Intent rootIntent) {
        MyApplication.getNoteManager(getBaseContext()).saveChanges();
        stopSelf();
    }
}
