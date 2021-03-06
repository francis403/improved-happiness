package com.example.gamethetown.Storage;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gamethetown.gameControllers.HotspotImagePuzzle;
import com.example.gamethetown.interfaces.GameController;
import com.example.gamethetown.item.Hotspot;

public class ImageHotspotGetter extends ImageGetter {

    private Hotspot h;
    private ImageView imageView;
    private RelativeLayout loading;
    private GameController gameController;

    /**
     *
     * @param imgView -> Place where we put the image
     * @param loading -> the loader to stop
     * @param gameController -> in case we need to do something when the loading is finished
     */
    public ImageHotspotGetter(ImageView imgView, RelativeLayout loading, GameController gameController) {
        this.imageView = imgView;
        this.loading = loading;
        this.gameController = gameController;
    }


    /**
     * Sets the image in the place it was told to set
     * @param feed -> image to set
     * @ensures imageView has image
     */
    protected void onPostExecute(Bitmap feed) {
        Log.e("Acabou","Acabou de ir buscar a foto");
        if(imageView != null)
            imageView.setImageBitmap(feed); //set the image
        if(loading != null){
            loading.setVisibility(View.GONE);
        }
        //if activity need photo to start
        if(gameController != null)
            gameController.start(imageView);
    }

}
