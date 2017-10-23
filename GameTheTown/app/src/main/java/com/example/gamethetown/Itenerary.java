package com.example.gamethetown;


import android.app.ActionBar;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class Itenerary extends App_Menu {

    private ImageSwitcher sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itenerary_new);

    }
}
