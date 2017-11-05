package com.example.gamethetown.gameControllers;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.Game;

//TODO -> receber a informacao toda do MapcurrentItinerary
public class HotspotQuiz extends App_Menu {

    private RadioGroup rg;
    private int correct_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_quiz);

        rg = (RadioGroup) findViewById(R.id.questions);
        correct_question = R.id.quest1; //teste //estava a pensar meter o id aqui e pronot

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == correct_question){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //TODO -> Do Something
                    return;
                }
                //tODO -> DO SOMETHING ELSE
            }

        });

    }


}
