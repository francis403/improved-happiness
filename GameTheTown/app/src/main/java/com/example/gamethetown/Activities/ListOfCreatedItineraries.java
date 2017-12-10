package com.example.gamethetown.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Database.DBConstants;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.interfaces.ClickListener;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;

import java.util.List;

public class ListOfCreatedItineraries extends App_Menu {

    private List<Itinerary> itenList;
    private RecyclerView recyclerView;
    private ItineraryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_created_itineraries);

        //itenList = user.getCreatedItineraries();

        setData();
    }

    public void setData(){
        if(findViewById(R.id.recycler_view2) != null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
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
                    //Itinerary iten = itenList.get(position);
                    //Toast.makeText(getApplicationContext(), iten.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                }

                //@Override
                public void onLongClick(View view, int position) {
                    /**
                    Intent intent = new Intent(getApplicationContext(),CreateItenerary.class);
                    intent.putExtra("hotList",mAdapter.getList().get(position));
                    startActivity(intent);
                     **/
                }
            }));

            idc.allUserItineraries(mAdapter,user.getUserID(),
                    DBConstants.REFERENCE_CREATED_ITEN);
            //new StorageDatabase().setPhotosInItineraryAdapter(mAdapter);
            //prepareItenData();
        }
    }

}
