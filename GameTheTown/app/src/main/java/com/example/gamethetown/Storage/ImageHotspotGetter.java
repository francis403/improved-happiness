package com.example.gamethetown.Storage;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamethetown.item.Hotspot;

/**
 * Created by franc on 04/12/2017.
 */

public class ImageHotspotGetter extends ImageGetter {

    private Hotspot h;
    private ImageView imageView;

    /**
     *
     * @param imageView -> where to set the image
     */
    public ImageHotspotGetter(ImageView imageView){
        this.imageView = imageView;
    }

    /**
     * Sets the image in the place it was told to set
     * @param feed -> image to set
     * @ensures imageView has image
     */
    protected void onPostExecute(Bitmap feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.e("Acabou","Acabou de ir buscar a foto");
        imageView.setImageBitmap(feed); //set the image
    }

}
