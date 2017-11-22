package com.example.gamethetown.Activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfCompletedItineraries extends App_Menu {

    private List<Itinerary> itenList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItineraryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        itenList = user.getCompletedItineraries();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_completed_itineraries);

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
                    recyclerView, new ItineraryCatalog.ClickListener() {
                //@Override
                public void onClick(View view, int position) {
                    Itinerary iten = itenList.get(position);
                    Toast.makeText(getApplicationContext(), iten.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                }

                //@Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }


}
