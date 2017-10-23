package com.example.gamethetown;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

/**
 * Created by franc on 22/10/2017.
 */

public class ListOfIteneraries extends App_Menu {

    private PopupWindow popwindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_itenerary);

    }


    public void onFilterPress(View view) {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_filter,null);
        //size
        popwindow = new PopupWindow(container,width,height/2,true);
        //position

        popwindow.showAtLocation((LinearLayout)findViewById(R.id.list_itenerary),
                Gravity.NO_GRAVITY,0,0);


    }

}
