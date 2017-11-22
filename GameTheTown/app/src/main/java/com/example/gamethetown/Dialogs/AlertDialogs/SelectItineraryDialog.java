package com.example.gamethetown.Dialogs.AlertDialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.gamethetown.Catalogs.UserCatalog;
import com.example.gamethetown.item.Itinerary;

public class SelectItineraryDialog extends AlertDialog.Builder {

    public SelectItineraryDialog(@NonNull Context context, final Itinerary iten) {
        super(context);
        this.setMessage("Do you want to set this as the current Itinerary?");
        this.setCancelable(false);
        this.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //confirm

                new UserCatalog().getCurrentUser()
                        .setCurrentItinerary(iten);
            }
        });
        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}
