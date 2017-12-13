package com.example.gamethetown.Storage;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gamethetown.interfaces.GameController;
import com.example.gamethetown.item.Hotspot;

public class ImageUserGetter extends ImageGetter {

    //CircleImageView

    private ImageView imageView;
    private RelativeLayout loading;

    /**
     *
     * @param imgView -> Place where we put the image
     * @param loading -> the loader to stop
     */
    public ImageUserGetter(ImageView imgView, RelativeLayout loading) {
        this.imageView = imgView;
        this.loading = loading;
    }


    /**
     * Sets the image in the place it was told to set
     * @param feed -> image to set
     * @ensures imageView has image
     */
    protected void onPostExecute(Bitmap feed) {
        if(imageView != null)
            imageView.setImageBitmap(feed); //set the image
        if(loading != null)
            loading.setVisibility(View.GONE);
    }

}
