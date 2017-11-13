package com.example.gamethetown.games;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.Game;
import com.example.gamethetown.item.Itinerary;

import java.io.Serializable;

/**
 * Created by franc on 05/11/2017.
 */
//TODO -> mudar o nome, nao sei se necessario
public abstract class CurrentGame extends Activity implements Serializable {

    protected static final int DEFAULT_IMAGE_ID = R.drawable.no_image;

    private String name;

    public CurrentGame(String name){this.name = name;}

    public String getGameName() {
        return name;
    }
    public void setName(String name){this.name = name;}

    public abstract void startGame();
    public abstract Class getGameClass();
    public abstract boolean isComplete();
    public abstract Double getScore();
}
