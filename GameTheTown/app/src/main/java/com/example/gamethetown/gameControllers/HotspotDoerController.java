package com.example.gamethetown.gameControllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.Storage.ImageHotspotGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.interfaces.GameController;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;

public abstract class HotspotDoerController extends App_Menu
        implements GameController{

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    protected Bundle extras;
    protected static boolean isCorrect = false,answeared = false;
    protected static double score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
    }

    public void setIsCorrect(Boolean isCorrect){this.isCorrect = isCorrect;}
    public void setAnsweared(Boolean answeared){this.answeared = answeared;}
    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("answeared",answeared);
        data.putExtra("passed",isCorrect);
        data.putExtra("score",score);
        setResult(RESULT_OK,data);
        super.finish();
    }

    //TODO -> TO TESTE
    public String getDuration(long time){
        StringBuilder sb = new StringBuilder();
        double minutes;
        double seconds;
        if(time > MINUTE){
            minutes = time/MINUTE;
            sb.append(Double.parseDouble(new DecimalFormat("##.##").format(minutes)) + " min");
            time -= MINUTE*minutes; //vamos retirar todos os minutos
        }
        if(time > SECOND){
            seconds = time/ SECOND;
            sb.append(Double.parseDouble(new DecimalFormat("##.##").format(seconds)) + " s");
        }
        return sb.toString();
    }

    /**
     * Sets the photo on the specific Hotspot
     * @param imgView -> Place to put the image
     * @param loading -> Loading screen to end
     * @param gameController -> In case we need to do something once we finish loading the game
     */
    public void setPhoto(final ImageView imgView, final RelativeLayout loading,
                         final GameController gameController){
        StorageDatabase sd = new StorageDatabase();
        Itinerary iten = user.getCurrentItinerary().getCurrentItinerary();
        sd.getHotspotPhoto(iten.getItenID(),user.getCurrentItinerary().getIndice() + 1).
                addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri o) {
                        new ImageHotspotGetter(imgView,loading,gameController).execute(o.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.GONE);
                imgView.setImageResource(R.drawable.no_image);
            }
        });
    }

}
