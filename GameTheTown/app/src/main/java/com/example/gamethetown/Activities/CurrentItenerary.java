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
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.Storage.ImageItineraryGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.item.CurrentItinerary;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

//TODO -> precisa de receber a informacao toda
public class CurrentItenerary extends App_Menu {

    private Itinerary iten;

    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.description) TextView description;
    @InjectView(R.id.user_profile_photo) CircleImageView creatorImages;
    @InjectView(R.id.imageOfItinerary) LinearLayout img;

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
            //TODO -> preciso ir buscar o curr_iten da net
            //TODO -> preciso de ir buscar o criador

            iten = currIten.getCurrentItinerary();
            title.setText(iten.getName());
            description.setText(iten.getDescription());

            //TODO -> vou ter de mudar para ir buscar a foto do utilizador
            //creatorImages.setImageResource(iten.getCreator().getImageID());

            //Caso ja o tenhamos
            Bitmap bitmap;
            if((bitmap = iten.getImageBitmap()) != null){
                Drawable d = new BitmapDrawable(bitmap);
                img.setBackground(d);
                return;
            }
            //caso ainda nao tenhamos vamos buscar ha base de dados
            StorageDatabase sd = new StorageDatabase();
            sd.getItenPhoto(iten.getItenID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri o) {
                    ImageItineraryGetter iig = new ImageItineraryGetter(iten,img);
                    iig.execute(o.toString());
                }
            });
            //em ultimo caso procuramos no telefone
            String path;
            if((path = iten.getImagePath()) != null){
                Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeFile(path));
                img.setBackground(d);
            }
            else
                img.setBackgroundResource(iten.getImageID());
        }

    }

    public void goToMap(View view){
        Intent intent = new Intent(this, Map_Current_Iten.class);
        startActivity(intent);
    }
}
