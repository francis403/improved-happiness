package com.example.gamethetown.games;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.Database.DBConstants;
import com.example.gamethetown.gameControllers.HotspotQuiz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Quiz extends CurrentGame {

    private static final double CORRECT_SCORE = 50;
    private static final int NUMBER_OF_QUESTIONS = 4;

    private String question;
    private String[] aws;
    private int correctAsw;
    private int imageID;
    private String imagePath;

    public Quiz(){
        super("Quiz");
        aws = new String[NUMBER_OF_QUESTIONS];
        imageID = DEFAULT_IMAGE_ID;
    }

    public Quiz(DataSnapshot snap){
        super(snap,"Quiz");
        imageID = DEFAULT_IMAGE_ID;
    }

    @Override
    public void setPhoto(String itenID, int i) {
        StorageDatabase storage = new StorageDatabase();
        storage.setHotspotPhoto(itenID,i, BitmapFactory.decodeFile(imagePath));
    }

    @Override
    public void startGame() {
        //Do nothing
    }

    @Override
    public Class getGameClass(){return HotspotQuiz.class;}

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

    @Override
    public void setValueInDatabase(DatabaseReference parentRef, Object obj) {
        DatabaseReference gameRef = parentRef.child("game");
        gameRef.child("type").setValue("Quiz");
        for(int i = 0 ; i < NUMBER_OF_QUESTIONS; i++)
            gameRef.child("asw_" + i).setValue(aws[i]);
        gameRef.child("correct_ans").setValue(correctAsw);
        gameRef.child("question").setValue(question);
    }

    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {
        DataSnapshot gameSnap = snap.child(DBConstants.REFERENCE_GAME);
        this.question = gameSnap.child("question").getValue(String.class);
        if(aws == null)
            aws = new String[NUMBER_OF_QUESTIONS];
        for(int i = 0 ; i < aws.length ; i++) {
            aws[i] = gameSnap.child("asw_" + i).getValue(String.class);
            Log.e("DES",aws[i]);
        }
        this.correctAsw = gameSnap.child("correct_ans").getValue(Integer.class);
        this.imageID = DEFAULT_IMAGE_ID;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
