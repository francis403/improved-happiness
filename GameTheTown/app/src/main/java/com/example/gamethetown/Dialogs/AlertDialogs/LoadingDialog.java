package com.example.gamethetown.Dialogs.AlertDialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class LoadingDialog extends ProgressDialog {

    public LoadingDialog(@NonNull Context context,String message) {
        super(context);
        this.setMessage(message);
        this.setCancelable(false);
        this.setIndeterminate(true);
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
