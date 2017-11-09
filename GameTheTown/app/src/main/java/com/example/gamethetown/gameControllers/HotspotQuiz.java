package com.example.gamethetown.gameControllers;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.interfaces.Game;

import org.w3c.dom.Text;

//TODO -> receber a informacao toda do MapcurrentItinerary
public class HotspotQuiz extends App_Menu {

    private RadioGroup rg;
    private int correct_question;
    private Quiz quiz;
    private Bundle extras;
    private boolean isCorrect = false,answeared = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_quiz);

        extras = getIntent().getExtras();
        if(extras != null){
            quiz = (Quiz) extras.get("game");

            android.support.v7.app.ActionBar ab = getSupportActionBar();
            String title = quiz.getGameName();
            ab.setTitle(title);

            ((ImageView) findViewById(R.id.image)).setImageResource(quiz.getImageID());

            ((TextView) findViewById(R.id.question)).setText(quiz.getQuestion());
            ((TextView) findViewById(R.id.quest1)).setText(quiz.getAsw1());
            ((TextView) findViewById(R.id.quest2)).setText(quiz.getAsw2());
            ((TextView) findViewById(R.id.quest3)).setText(quiz.getAsw3());
            ((TextView) findViewById(R.id.quest4)).setText(quiz.getAsw4());
            switch (quiz.getCorrectAsw()){
                case 0:
                    correct_question = R.id.quest1;
                    break;
                case 1:
                    correct_question = R.id.quest2;
                    break;
                case 2:
                    correct_question = R.id.quest3;
                    break;
                case 3:
                    correct_question = R.id.quest4;
                    break;
            }

        }

        rg = (RadioGroup) findViewById(R.id.questions);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == correct_question){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //TODO -> Do Something

                    isCorrect = true;
                }
                else
                    isCorrect = false; //nao deve ser necessario mas ja agora copy pasta
                answeared = true;
                finish();
                return;
            }

        });

    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("answeared",answeared);
        data.putExtra("passed",isCorrect);

        setResult(RESULT_OK,data);
        super.finish();
    }

}
