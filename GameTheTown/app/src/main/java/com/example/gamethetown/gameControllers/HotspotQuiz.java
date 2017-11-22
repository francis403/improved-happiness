package com.example.gamethetown.gameControllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.interfaces.Game;

import org.w3c.dom.Text;

//TODO -> receber a informacao toda do MapcurrentItinerary
public class HotspotQuiz extends HotspotDoerController {

    private RadioGroup rg;
    private int correct_question;
    private Quiz quiz;
    private Bundle extras;
    private ImagePicker imgPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_quiz);

        imgPicker = ImagePicker.create(this).returnAfterFirst(true)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")
                .enableLog(false);

        score = 0;
        isCorrect = false;
        answeared = false;
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
                    //TODO -> Do Something
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


}
