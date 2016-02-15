package com.andela.voluminotesapp.utilities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;

import com.andela.notelib.note.PaperNote;

/**
 * Created by andela on 2/14/16.
 */
public class ShareNote {
    private Intent shareIntent;
    private Activity activity;

    public ShareNote() {
    }

    public void sharePaperNote(Activity activity, PaperNote note){
        this.activity = activity;
        shareIntent = ShareCompat.IntentBuilder
                .from(this.activity)
                .setType("text/html")
                .addEmailTo("")
                .setSubject(note.getTitle())
                .setHtmlText(note.getNote())
                .getIntent();

        sendNote();
    }

    private void sendNote(){
        if (shareIntent.resolveActivity(this.activity.getPackageManager()) != null){
            this.activity.startActivity(shareIntent);
        }
    }

}
