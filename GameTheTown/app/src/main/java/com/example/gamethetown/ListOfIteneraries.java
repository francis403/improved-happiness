package com.example.gamethetown;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.fragment.FilterFragment;
import com.example.gamethetown.fragment.List_Test;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;
//import com.example.gamethetown.item.RecyclerTouchListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by franc on 22/10/2017.
 */

public class ListOfIteneraries extends App_Menu {

    private PopupWindow popwindow; //TODO -> RETIRAR
    private LayoutInflater layoutInflater;
    private List<Itinerary> itenList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItineraryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_itenerary);

        setData();

    }

    public void setData(){
        if(findViewById(R.id.recycler_view) != null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new ItineraryAdapter(itenList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    recyclerView, new ClickListener() {
                //@Override
                public void onClick(View view, int position) {
                    Itinerary iten = itenList.get(position);
                    Toast.makeText(getApplicationContext(), iten.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                }

                //@Override
                public void onLongClick(View view, int position) {

                }
            }));
            prepareItenData();
        }
    }
    //TODO
    public void changeFrag(View view){
        FilterFragment ff = new FilterFragment();
        ff.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //add the fragment to the fragment container FrameLayout
        transaction.replace(R.id.list_frag,ff);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    //TODO
    public void setFragList(View view){
        List_Test ft = new List_Test();
        ft.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //add the fragment to the fragment container FrameLayout
        transaction.replace(R.id.list_frag,ft);

        transaction.addToBackStack(null);
        transaction.commit();
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

        popwindow.showAtLocation((ConstraintLayout)findViewById(R.id.list_itenerary),
                Gravity.NO_GRAVITY,0,0);


    }

    private void prepareItenData() {



        Itinerary iten = new Itinerary("Um passeio por Lisboa", new Date(2017,9,10));
        itenList.add(iten);

        iten = new Itinerary("Aventura pelo Monteiro Dos Jeronimos", new Date(2017,9,11),R.drawable.monteirodojeronimo);
        itenList.add(iten);

        iten = new Itinerary("Vamos conhecer a Fcul!", new Date(2017,7,12));
        itenList.add(iten);

        iten = new Itinerary("Vamos Nadar Com os Peixes - Oceanario De Lisboa", new Date(2017,9,11));
        itenList.add(iten);

        iten = new Itinerary("Alfama? Mal a Conheco!", new Date(2017,7,12));
        itenList.add(iten);

        iten = new Itinerary("Vamos Assaltar Palacio Chiado!", new Date(2017,7,12));
        itenList.add(iten);

        iten = new Itinerary("Sera que o Rio Tejo eh mesmo um Rio?", new Date(2017,7,12));
        itenList.add(iten);

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
