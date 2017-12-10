package com.example.gamethetown.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.HotspotAdapter;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;

import java.util.HashMap;
import java.util.function.Function;

//TODO -> tentar nao precisar dos tabs e tentar obter a vista aqui mesmo

/**
 * Use of Filter, requires that we pass what will happen once we press the submit button
 */
public class Filter extends BottomSheetBehavior {

    private int maxDistance;

    /**
     *
     * @param view -> the view we will use
     * @param context -> context to know where we are
     */
    public Filter(final View view, Context context,
                  final Tab1List tab1,final Tab2Map tab2){

        final Spinner spinnerDif = (Spinner) view.findViewById(R.id.spinner_dif);
        final TextView filterText = (TextView) view.findViewById(R.id.textProx);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxDistance = progress;
                if(maxDistance == 0)
                    filterText.setText("Max distance: No Max");
                else
                    filterText.setText("Max distance: " + maxDistance + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1,Difficulties.values());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDif.setAdapter(adapter);

        Button confirmSearch = (Button) view.findViewById(R.id.search);
        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = ((EditText) view.findViewById(R.id.paramFilter))
                        .getText().toString();
                Difficulties selectedDif = (Difficulties) spinnerDif.getSelectedItem();

                //tab1.setFilteredData(search,selectedDif,maxDistance,tab2.getLocation());
                maxDistance = 0;

            }
        });


    }


}
