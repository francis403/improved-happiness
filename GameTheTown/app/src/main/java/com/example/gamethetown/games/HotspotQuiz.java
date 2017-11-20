package com.example.gamethetown.games;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.Game;

public class HotspotQuiz extends App_Menu implements Game {

    private static final String name = "Quiz";
    private boolean finished = false,correct = false;
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

                finished = true;

                if(checkedId == correct_question){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //TODO -> Do Something
                    correct = true;
                    return;
                }
                //tODO -> DO SOMETHING ELSE
            }

        });

    }

    @Override
    public String getGameName() {
        return name;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isCorrect() {
        return correct;
    }

    @Override
    public boolean isComplete() { return true; }
}
