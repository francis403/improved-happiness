package com.example.gamethetown.games;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.gamethetown.R;
import com.example.gamethetown.gameControllers.HotspotImagePuzzle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//// TODO: 11/11/2017 -> class is incomplete
public class ImagePuzzle extends CurrentGame {

    private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;
    private ImageView originalImage;
    private static final double MAX_SCORE = 200;
    //for score purposes
    private int numberOfMoves;
    private int imageID = R.drawable.no_image;
    private String imagePath;
    private ArrayList<Bitmap> chunkedImage;

    public ImagePuzzle(){
        super("ImagePuzzle");
    }

    public ImagePuzzle(int imageID){
        super("ImagePuzzle");
        this.imageID = imageID;
    }

    public void incrementMoves(){numberOfMoves++;}
    public void setDefaultMoves(){numberOfMoves = 0;}
    public int getMoves(){return numberOfMoves;}

    @Override
    public void startGame() {
        //Do nothing
    }

    @Override
    public Class getGameClass() {
        return HotspotImagePuzzle.class;
    }

    //TODO -> Tenho que verificar que tem uma imagem
    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public Double getScore() {
        Log.e("NumberOfMoves","Num: " + numberOfMoves);
        if(numberOfMoves < 10)
            return MAX_SCORE;
        if(numberOfMoves < 15)
            return MAX_SCORE * 0.75;
        if(numberOfMoves < 19)
            return MAX_SCORE * 0.5;

        return MAX_SCORE * 0.2;
    }

    @Override
    public void setValueInDatabase(DatabaseReference parentRef, Object obj) {
        DatabaseReference gameRef = parentRef.child("game");
        gameRef.child("type").setValue(getGameName());
        //TODO -> meter a imagem
    }
    //TODO
    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {

    }

    public int getImageID(){return imageID;}
    public String getImagePath(){return imagePath;}

    public void setImageID(int imageID){this.imageID = imageID;}
    public void setImagePath(String imagePath){this.imagePath = imagePath;}

    /**
     * Splits the source image and show them all into a grid in a new activity
     *
     * @param image
     *            The source image to split
     */
    //TODO -> need to split the image well enough
    public void splitImage(ImageView image) {
        // Getting the scaled bitmap of the source image
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        // To store all the small image chunks in bitmap format in this list
        chunkedImage = new ArrayList<Bitmap>(ROWS * COLUMNS);

        int chunkSideLength =bitmap.getHeight()/ROWS;
        int chunkWideLength = bitmap.getWidth()/COLUMNS;
        // picture perfectly splits into square chunks
        int yCoord = 0;
        for (int y = 0; y < ROWS; ++y) {
            int xCoord = 0;
            for (int x = 0; x < COLUMNS; ++x) {
                chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWideLength, chunkSideLength));
                xCoord += chunkWideLength;
            }
            yCoord += chunkSideLength;
        }
    }

    public void setMoves(int numberOfMoves){this.numberOfMoves = numberOfMoves;}


    public List<Bitmap> getChunkedImage(){return chunkedImage;}

    //clase de teste
    public Drawable getImage(){
        Random random = new Random();

        return new BitmapDrawable(chunkedImage.get(random.nextInt(9)));
    }

}
