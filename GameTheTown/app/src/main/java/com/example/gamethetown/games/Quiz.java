package com.example.gamethetown.games;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.gamethetown.gameControllers.HotspotQuiz;
import com.example.gamethetown.interfaces.Game;
import com.example.gamethetown.item.Hotspot;

import java.io.Serializable;

/**
 * Created by franc on 05/11/2017.
 */
//TODO -> ainda a pensar como implementar
public class Quiz extends CurrentGame {

    private static final double CORRECT_SCORE = 50;
    private static final int NUMBER_OF_QUESTIONS = 4;

    private String question;
    private String[] aws;
    private int correctAsw = -1;
    private int imageID;

    public Quiz(){
        super("Quiz");
        aws = new String[NUMBER_OF_QUESTIONS];
        imageID = DEFAULT_IMAGE_ID;
    }

    @Override
    public Class getGameClass(){return HotspotQuiz.class;}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isCorrect() {
        return false;
    }

    public boolean isComplete(){
        for(String s : aws)
            if(s == null || s.equals(""))
                return false;
        return question != null && !question.equals("") && correctAsw != -1;
    }

    @Override
    public Double getScore() {
        return CORRECT_SCORE;
    }

    public int getImageID(){return imageID;}

    public void setImageID(int imageID){this.imageID = imageID;}
    public void setQuestion(String question){this.question = question;}
    public void setAsw1(String asw1){aws[0] = asw1;}
    public void setAsw2(String asw2){aws[1] = asw2;}
    public void setAsw3(String asw3){aws[2] = asw3;}
    public void setAsw4(String asw4){aws[3] = asw4;}
    public void setCorrectAsw(int checked){correctAsw = checked;}

    public int getCorrectAsw(){return correctAsw;}
    public String getQuestion(){return question;}
    public String getAsw1(){return aws[0];}
    public String getAsw2(){return aws[1];}
    public String getAsw3(){return aws[2];}
    public String getAsw4(){return aws[3];}

}
