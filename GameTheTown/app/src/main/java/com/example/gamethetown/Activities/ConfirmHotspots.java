package com.example.gamethetown.Activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.HotspotAdapter;
import com.example.gamethetown.adapters.SimpleItemTouchHelperCallback;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmHotspots extends App_Menu {

    private HotspotAdapter mAdapter;
    private RecyclerView recyclerView;
    private boolean isPressed;
    private Bundle extras;
    private HashMap<String,Hotspot> preHotspots;
    private Itinerary itenToCreate;
    private ArrayList<Hotspot> hotList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_hotspots);

        isPressed = false;
        mAdapter = new HotspotAdapter(hotList);

        extras = getIntent().getExtras();
        preHotspots = (HashMap<String, Hotspot>) extras.get("preList");
        for(Hotspot h : preHotspots.values())
            hotList.add(h);


        itenToCreate = (Itinerary) extras.get("itinerary");
        ((ImageView) findViewById(R.id.image)).setImageResource(itenToCreate.getImageID());
        ((TextView) findViewById(R.id.description)).setText(itenToCreate.getDescription());
        ((TextView) findViewById(R.id.dist)).setText("Distance: aprox"
                + mAdapter.getTotalWalkingDistance() + " meters");
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        String title = itenToCreate.getTitle();
        ab.setTitle(itenToCreate.getTitle());

        setData();
    }

    public void setData(){
        if(findViewById(R.id.recycler_view) != null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    recyclerView, new ListOfIteneraries.ClickListener() {
                //@Override
                public void onClick(View view, int position) {

                }

                //@Override
                public void onLongClick(View view, int position) {

                }
            }));
            //prepareItenData();
        }
    }
    @Override
    public void finish(){
        //TODO
        //quero enviar para traz a lista nova
        super.finish();
    }

    public void userFinish(View view){
        if(!isPressed) {
            isPressed = true;
            for(Hotspot h : preHotspots.values())
                itenToCreate.addHotspot(h);
            user.addCreatedItinerary(itenToCreate);
            Toast.makeText(getBaseContext(), "Created the Itinerary", Toast.LENGTH_SHORT).show();
        }
    }

}
