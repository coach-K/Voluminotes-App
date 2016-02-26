package com.andela.voluminotesapp.utilities;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class MsgBox {
    private static Toast toast;
    private static Snackbar snackbar;

    public static void show(Context context, String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void show(View view, String message, String action, View.OnClickListener clickListener) {
        if (snackbar != null)
            snackbar.dismiss();
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(action, clickListener);
        snackbar.show();
    }
}
