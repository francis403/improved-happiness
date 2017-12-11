package com.example.gamethetown.Activities;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.Storage.ImageItineraryGetter;
import com.example.gamethetown.Storage.ImageUserGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.item.CurrentItinerary;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentItenerary extends App_Menu {

    private Itinerary iten;

    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.description) TextView description;
    @InjectView(R.id.user_profile_photo) CircleImageView creatorImages;
    @InjectView(R.id.imageOfItinerary) LinearLayout img;
    @InjectView(R.id.loading) RelativeLayout loading;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itenerary_new);
        ButterKnife.inject(this);
        CurrentItinerary currIten = user.getCurrentItinerary();
        //TODO -> mudar isto depois
        if( currIten == null || currIten.getCurrentItinerary() == null){
            //do something
            (findViewById(R.id.current_iten)).setVisibility(View.INVISIBLE);
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
            aBuilder.setMessage("No Itinerary Selected");
            aBuilder.setCancelable(false);
            aBuilder.setPositiveButton("Select One", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //confirm
                    //nao sei se posso dar isto null
                    selectItenerary(null);
                    finish();
                }
            });
            aBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            aBuilder.show();
        }
        else{
            //TODO -> preciso de ir buscar o itinerario

            iten = currIten.getCurrentItinerary();
            title.setText(iten.getName());
            description.setText(iten.getDescription());

            //TODO -> vou ter de mudar para ir buscar a foto do utilizador
            //creatorImages.setImageResource(iten.getCreator().getImageID());
            //buscar a foto do util
            StorageDatabase storageDatabase = new StorageDatabase();
            storageDatabase.getUserPhoto(iten.getUserID()).
                    addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri o) {
                    Log.e("Sucess in loading","Sucess in Loading");
                    ImageUserGetter iug = new ImageUserGetter(creatorImages,null);
                    iug.execute(o.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    //do nothing
                }
            });

            //Caso ja o tenhamos
            Bitmap bitmap;
            if((bitmap = iten.getImageBitmap()) != null){
                Drawable d = new BitmapDrawable(bitmap);
                img.setBackground(d);
                loading.setVisibility(View.GONE);
                return;
            }
            //caso ainda nao tenhamos vamos buscar ha base de dados
            StorageDatabase sd = new StorageDatabase();
            sd.getItenPhoto(iten.getItenID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri o) {
                    ImageItineraryGetter iig = new ImageItineraryGetter(iten,img);
                    iig.setLoader(loading);
                    iig.execute(o.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    img.setBackgroundResource(R.drawable.no_image);
                    loading.setVisibility(View.GONE);
                }
            });
        }

    }

    public void goToMap(View view){
        Intent intent = new Intent(this, Map_Current_Iten.class);
        startActivity(intent);
    }
}
