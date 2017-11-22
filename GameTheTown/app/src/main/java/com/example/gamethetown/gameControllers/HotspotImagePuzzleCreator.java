package com.example.gamethetown.gameControllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.ImagePuzzle;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by franc on 13/11/2017.
 */

public class HotspotImagePuzzleCreator extends App_Menu {

    private static final int PICK_FROM_CAMERA = 1 ;
    private static final int PICK_FROM_FILE = 2;
    private Bundle extras;
    private ImagePuzzle game;
    private ImageView originalImage;
    private Uri imageCaptureUri;
    private ImagePicker imgPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_image_puzzle_creator);
        extras = getIntent().getExtras();

        imgPicker = ImagePicker.create(this).returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .limit(10) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false);

        originalImage = (ImageView) findViewById(R.id.image);
        CurrentGame cg = (CurrentGame) extras.get("currentGame");
        if(cg != null && !cg.getGameName().equals("ImagePuzzle"))
            game = null;
        else
            game = (ImagePuzzle) extras.get("currentGame");
        if(game != null){
            //set the things
            String path = game.getImagePath();
            if(path != null){
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                originalImage.setImageBitmap(bitmap);
            }
        }
        else
            game = new ImagePuzzle();
        //need to get the things
    }

    //meter isto num controler
    public void selectImage(View v) {
        String[] items = new String[]{"From Cam", "From SD Card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,items);
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setTitle("Select Image");
        aBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){

                    /**
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),"tmp_avatar" +
                            String.valueOf(System.currentTimeMillis() + ".jpg"));
                    imageCaptureUri = Uri.fromFile(file);
                    try{
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageCaptureUri);
                        intent.putExtra("return data",true);
                        startActivityForResult(intent,PICK_FROM_CAMERA);
                    }catch (Exception e){

                    }
                    **/
                }
                else{
                    imgPicker.start(PICK_FROM_FILE);
                }
            }
        });
        aBuilder.show();
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

    public String getPathFromUri(Uri uri){
        String []proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        if(cursor == null) return null;
        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(colum_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        Bitmap bitmap = null;
        String path = "";
        //originalImage.setImageBitmap(bitmap);

        if(requestCode == PICK_FROM_FILE){
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Image image = images.get(0);
            path = image.getPath();
            /**
            imageCaptureUri = data.getData();
            if(getPath(imageCaptureUri) == null)
                Log.e("ERROR", "Nao esta a encontrar mesmo a imagem");
            path = getPathFromUri(imageCaptureUri);
             **/
            if(path == null) {
                Log.e("ERROR", "Path esta a dar null");
                path = imageCaptureUri.getPath();
            }
            if(path != null) {
                Log.e("Path", "Path: " + path);
                bitmap = BitmapFactory.decodeFile(path);
            }

        }
        else if(requestCode == PICK_FROM_CAMERA){
            path = imageCaptureUri.getPath();
            bitmap = BitmapFactory.decodeFile(path);
        }
        if(bitmap != null) {
            originalImage.setImageBitmap(bitmap);
            game.setImagePath(path);
        }
    }


}
