package com.example.gamethetown.games;

import android.content.Context;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;

public class Hotspot_Quiz_Creator extends App_Menu {

    private RadioGroup rg;
    private int correct_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot__quiz_creator);
    }



}
