package com.example.gamethetown.gameControllers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;

/**
 * Created by franc on 13/11/2017.
 */

public class HotspotImagePuzzleCreator extends App_Menu {

    private Bundle extras;
    private ImagePuzzle game;
    private ImageView originalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_image_puzzle_creator);
        extras = getIntent().getExtras();

        originalImage = (ImageView) findViewById(R.id.image);
        CurrentGame cg = (CurrentGame) extras.get("currentGame");
        if(cg != null && !cg.getGameName().equals("ImagePuzzle"))
            game = null;
        else
            game = (ImagePuzzle) extras.get("currentGame");
        if(game != null){
            //set the things
        }
        else
            game = new ImagePuzzle();
        //need to get the things
    }

    //meter isto num controler
    public void selectImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    //set all the info
    public void onButtonFinish(View view){finish();}

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("markerID",(String) extras.get("markerID"));
        data.putExtra("game",game);

        setResult(RESULT_OK,data);
        super.finish();
    }

    //meter isto num controler //TODO
    //TODO esta a dar erro, a imagem nao aparece
    private Bitmap getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        //cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = getPath(data.getData());
            originalImage.setImageBitmap(bitmap);
        }
    }

}
