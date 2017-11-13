package com.example.gamethetown.gameControllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.GestureDetectGridView;
import com.example.gamethetown.games.ImagePuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HotspotImagePuzzle extends HotspotDoerController {

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;
    private static ImagePuzzle imgPuzzle;
    private static TextView tx;

    private static List<Bitmap> seperatedImage;
    private static GestureDetectGridView mGridView;
    private static String[] tileList;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_image_puzzle);

        tx = (TextView) findViewById(R.id.moves);

        if(extras != null) {
            imgPuzzle = (ImagePuzzle) extras.get("game");
            ImageView test = new ImageView(this);
            //test.setImageResource(R.drawable.background3);
            test.setImageResource(imgPuzzle.getImageID());
            imgPuzzle.splitImage(test);
            seperatedImage = imgPuzzle.getChunkedImage();
            init();
            scramble();
            display(getApplicationContext());
        }

    }

    private void init() {
        isCorrect = false;
        answeared = false;
        score = 0;
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);
        imgPuzzle.setDefaultMoves();
        tx.setText("Moves: " + imgPuzzle.getMoves());

        tileList = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = String.valueOf(i);
        }
    }
    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private static void swap(Context context, int currentPosition, int swap) {
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (isSolved()){
            isCorrect = true;
            answeared = true;
            score = imgPuzzle.getScore();
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }

    private static void display(Context context) {

        List<Bitmap> bitmaps = new ArrayList<>();
        Bitmap bitmap;
        for(int i = 0; i < tileList.length; i++){
            if(tileList[i].equals("0"))
                bitmap =  seperatedImage.get(0);
            else if(tileList[i].equals("1"))
                bitmap =  seperatedImage.get(1);
            else if(tileList[i].equals("2"))
                bitmap =  seperatedImage.get(2);
            else if(tileList[i].equals("3"))
                bitmap =  seperatedImage.get(3);
            else if(tileList[i].equals("4"))
                bitmap =  seperatedImage.get(4);
            else if(tileList[i].equals("5"))
                bitmap =  seperatedImage.get(5);
            else if(tileList[i].equals("6"))
                bitmap =  seperatedImage.get(6);
            else if(tileList[i].equals("7"))
                bitmap =  seperatedImage.get(7);
            else
                bitmap =  seperatedImage.get(8);
            bitmaps.add(bitmap);
        }

        mGridView.setAdapter(new ImageAdapter(context,bitmaps));
    }

    public static void moveTiles(Context context, String direction, int position) {
        // Upper-left-corner tile
        if(!answeared)
            imgPuzzle.incrementMoves();
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
        tx.setText("Moves: " + imgPuzzle.getMoves());
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                return false;
            }
        }

        return solved;
    }


    public static class ImageAdapter extends BaseAdapter{
        private Context mContext;

        private List<Bitmap> chunkedImage;

        public ImageAdapter(Context c,List<Bitmap> chunks){
            mContext = c;
            this.chunkedImage = chunks;
        }
        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(new BitmapDrawable(chunkedImage.get(position)));
            imageView.setAdjustViewBounds(true);
            return imageView;
        }

        @Override
        public int getCount() {
            return chunkedImage.size();
        }

        @Override
        public Object getItem(int position) {
            return chunkedImage.get(position);
        }

        //nao sei se esta vem feito
        @Override
        public long getItemId(int position) {
            return chunkedImage.get(position).getGenerationId();
        }
    }

}
