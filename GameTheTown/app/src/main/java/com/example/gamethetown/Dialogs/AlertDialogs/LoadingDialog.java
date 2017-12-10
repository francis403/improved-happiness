package com.example.gamethetown.Dialogs.AlertDialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.gamethetown.Catalogs.UserAuthentication;
import com.example.gamethetown.item.Itinerary;

public class LoadingDialog extends ProgressDialog {

    public LoadingDialog(@NonNull Context context,String message) {
        super(context);
        this.setMessage(message);
        this.setCancelable(false);
        this.setIndeterminate(true);
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
