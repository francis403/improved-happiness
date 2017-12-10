package com.example.gamethetown.gameControllers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.R;
import com.example.gamethetown.games.Quiz;

public class HotspotQuiz extends HotspotDoerController {

    private RadioGroup rg;
    private int correct_question;
    private Quiz quiz;
    private Bundle extras;
    private ImageView imgView;
    private RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_quiz);

        loading = (RelativeLayout) findViewById(R.id.loading);

        score = 0;
        isCorrect = false;
        answeared = false;
        extras = getIntent().getExtras();
        if(extras != null){
            quiz = (Quiz) extras.get("game");

            android.support.v7.app.ActionBar ab = getSupportActionBar();
            String title = quiz.getGameName();
            ab.setTitle(title);

            imgView = ((ImageView) findViewById(R.id.image));
            //imgView.setImageResource(quiz.getImageID());

            setPhoto(imgView,loading,null);

            ((TextView) findViewById(R.id.question)).setText(quiz.getQuestion());
            ((TextView) findViewById(R.id.quest1)).setText(quiz.getAsw1());
            ((TextView) findViewById(R.id.quest2)).setText(quiz.getAsw2());
            ((TextView) findViewById(R.id.quest3)).setText(quiz.getAsw3());
            ((TextView) findViewById(R.id.quest4)).setText(quiz.getAsw4());
            String path;
            if((path = quiz.getImagePath()) != null)
                ((ImageView) findViewById(R.id.image)).
                        setImageBitmap(BitmapFactory.decodeFile(path));
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
                if(answeared)
                    return;
                if(checkedId == correct_question){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    isCorrect = true;
                    score = quiz.getScore();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong!\n " +
                            ((RadioButton)findViewById(correct_question)).getText()
                            + "is correct ", Toast.LENGTH_SHORT).show();
                    isCorrect = false; //nao deve ser necessario mas ja agora copy pasta
                }
                answeared = true;
                //finish();
                return;
            }

        });

    }


    @Override
    public void start(Object o) {
        //do nothing
    }
}
