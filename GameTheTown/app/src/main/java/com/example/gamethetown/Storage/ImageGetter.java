package com.example.gamethetown.Storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by franc on 04/12/2017.
 */
//TODO -> eh possivel juntar todos estes num so?
public class ImageGetter extends AsyncTask<String, Void, Bitmap> {

    private Exception exception;

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }
}
