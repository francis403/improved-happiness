package com.example.gamethetown.games;

import com.example.gamethetown.interfaces.Game;

import java.io.Serializable;

/**
 * Created by franc on 05/11/2017.
 */
//TODO -> mudar o nome, nao sei se necessario
public abstract class CurrentGame implements Game,Serializable {

    private String name;

    public CurrentGame(String name){this.name = name;}
    @Override
    public String getGameName() {
        return name;
    }
    public void setName(String name){this.name = name;}

    @Override
    public abstract boolean isFinished();
    @Override
    public abstract boolean isCorrect();
    @Override
    public abstract boolean isComplete();
}
