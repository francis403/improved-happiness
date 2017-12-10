package com.example.gamethetown.Storage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gamethetown.item.Itinerary;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by franc on 04/12/2017.
 */

public class ImageItineraryGetter extends ImageGetter {

    private Exception exception;
    //o itinerario a adicionar a foto
    private Itinerary iten;

    private RecyclerView.Adapter adapter;
    private LinearLayout img;
    private RelativeLayout loader;
    //todo mudar para um objecto que cons
    //em vez de iten mudar para uma interface que receba tudo o que possa vir para aqui
    public ImageItineraryGetter(Itinerary iten, RecyclerView.Adapter adapter) {
        this.iten = iten;
        this.adapter = adapter;
    }

    public ImageItineraryGetter(Itinerary iten, LinearLayout img){
        this.iten = iten;
        this.img = img;
    }


    public void setLoader(RelativeLayout loader){
        this.loader = loader;
    }
    /** //ideia
    @Override
    protected void onPreExecute(){

        if(adapter != null){}
    }
    **/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onPostExecute(Bitmap feed) {
        iten.setImageBitmap(feed);
        if(adapter != null)
            adapter.notifyDataSetChanged();
        if(img != null) {
            Drawable d = new BitmapDrawable(feed);
            img.setBackground(d);
        }
        if(loader != null){
            loader.setVisibility(View.GONE);
        }
        //notificamos que ja recebeu
    }

}


