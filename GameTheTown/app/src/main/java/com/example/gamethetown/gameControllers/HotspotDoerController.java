package com.example.gamethetown.gameControllers;

import android.content.Intent;
import android.os.Bundle;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;

import java.text.DecimalFormat;

/**
 * Created by franc on 12/11/2017.
 */

public class HotspotDoerController extends App_Menu {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    protected Bundle extras;
    protected static boolean isCorrect = false,answeared = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("answeared",answeared);
        data.putExtra("passed",isCorrect);
        setResult(RESULT_OK,data);
        super.finish();
    }

    //TODO -> TO TESTE
    public String getDuration(long time){
        StringBuilder sb = new StringBuilder();
        double minutes;
        if(time > MINUTE){
            minutes = time/MINUTE;
            sb.append(Double.parseDouble(new DecimalFormat("##.##").format(minutes)) + "min");
            time -= MINUTE*minutes; //vamos retirar todos os minutos
        }
        return "";
    }

}
