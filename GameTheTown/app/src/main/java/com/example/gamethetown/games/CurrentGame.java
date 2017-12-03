package com.example.gamethetown.games;

import android.app.Activity;

import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
//TODO -> Retirar daqui a activity
//TODO -> mudar o nome, nao sei se necessario
public abstract class CurrentGame extends Activity implements Serializable,InTheDatabase {

    protected static final int DEFAULT_IMAGE_ID = R.drawable.no_image;

    //cada um dos jogos vai ter isto
    //TODO -> Ideia, nao sei se funciona
    public CurrentGame(DataSnapshot snap,String name){
        getValueInDatabase(snap,null );
        this.name = name;
    }

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
