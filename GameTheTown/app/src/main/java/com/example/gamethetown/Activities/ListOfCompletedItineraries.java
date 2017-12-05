package com.example.gamethetown.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Storage.ImageItineraryGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.interfaces.ClickListener;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;
import com.google.android.gms.tasks.OnSuccessListener;

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
                    recyclerView, new ClickListener() {
                //@Override
                public void onClick(View view, int position) {
                    Itinerary iten = itenList.get(position);
                    Toast.makeText(getApplicationContext(), iten.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                }

                //@Override
                public void onLongClick(View view, int position) {

                }
            }));

            for(final Itinerary i : itenList) {
                StorageDatabase sb = new StorageDatabase();
                sb.getItenPhoto(i.getItenID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("SUCESS", "Sucess in getting the photo!");
                        Log.e("Image URL ", "URL = " + uri.getPath());
                        ImageItineraryGetter ig = new ImageItineraryGetter(i,mAdapter);
                        try {
                            ig.execute(uri.toString());
                            //while(ig.getStatus() != AsyncTask.Status.FINISHED);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }
    }


}
