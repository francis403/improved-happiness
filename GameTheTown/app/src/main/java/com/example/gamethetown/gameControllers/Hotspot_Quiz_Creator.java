package com.example.gamethetown.gameControllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.Quiz;

import java.util.ArrayList;

public class Hotspot_Quiz_Creator extends App_Menu {

    private static final int PICK_FROM_FILE = 1;

    private ImagePicker imgPicker;
    private RadioGroup rg;
    private int checked = -1;
    private TextView question,asw1,asw2,asw3,asw4;
    private ImageView imgView;
    private Bundle extras;
    // O jogo que vamos fazer
    private Quiz game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot__quiz_creator);

        imgPicker = ImagePicker.create(this).returnAfterFirst(true)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")
                .enableLog(false);

        rg = (RadioGroup) findViewById(R.id.questions);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.quest1:
                        checked = 0;
                        break;
                    case R.id.quest2:
                        checked = 1;
                        break;
                    case R.id.quest3:
                        checked = 2;
                        break;
                    case R.id.quest4:
                        checked = 3;
                        break;
                }
            }
        });

        question = (TextView) findViewById(R.id.question);
        asw1 = (TextView) findViewById(R.id.asw1);
        asw2 = (TextView) findViewById(R.id.asw2);
        asw3 = (TextView) findViewById(R.id.asw3);
        asw4 = (TextView) findViewById(R.id.asw4);
        imgView = (ImageView)findViewById(R.id.image);
        extras = getIntent().getExtras();
        CurrentGame cg = (CurrentGame) extras.get("currentGame");
        if(cg != null && !cg.getGameName().equals("Quiz"))
            game = null;
        else
            game = (Quiz) extras.get("currentGame");
        if(game != null){
            int c = game.getCorrectAsw();
            if(c != -1) {
                switch (c){
                    case 0:
                        rg.check(R.id.quest1);
                        break;
                    case 1:
                        rg.check(R.id.quest2);
                        break;
                    case 2:
                        rg.check(R.id.quest3);
                        break;
                    case 3:
                        rg.check(R.id.quest4);
                        break;
                }

            }
            //se ja existir la um jogo
            String q = game.getQuestion();
            question.setText(q);
            asw1.setText(game.getAsw1());
            asw2.setText(game.getAsw2());
            asw3.setText(game.getAsw3());
            asw4.setText(game.getAsw4());
            String path;
            if((path = game.getImagePath()) != null)
                imgView.setImageBitmap(BitmapFactory.decodeFile(path));

        }
        else
            game = new Quiz();
    }

    public void getImage(View view){
        imgPicker.start(PICK_FROM_FILE);
    }

    public void onButtonFinish(View view){
        if(checked != -1)
            game.setCorrectAsw(checked);
        if(question != null && !question.getText().equals(""))
            game.setQuestion(question.getText().toString());
        if(asw1 != null && !asw1.equals(""))
            game.setAsw1(asw1.getText().toString());
        if(asw2 != null && !asw2.getText().equals(""))
            game.setAsw2(asw2.getText().toString());
        if(asw3 != null && !asw3.equals(""))
            game.setAsw3(asw3.getText().toString());
        if(asw4 != null && !asw4.equals(""))
            game.setAsw4(asw4.getText().toString());
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK)
            return;

        // Check which request we're responding to
        if(requestCode == PICK_FROM_FILE){
            Bitmap bitmap;
            String path = "";
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Image image = images.get(0);
            path = image.getPath();
            game.setImagePath(path);
            imgView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("markerID",(String) extras.get("markerID"));
        data.putExtra("game",game);

        setResult(RESULT_OK,data);
        super.finish();
    }

}
