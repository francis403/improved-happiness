package com.example.gamethetown.Activities;

/**
 * Created by franc on 31/10/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;

import java.util.ArrayList;
import java.util.List;

public class Tab1List extends Fragment {

    private List<Itinerary> itenList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItineraryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1list, container, false);

        itenList = new ItineraryCatalog().getItinerariesFromDatabase();
        setData(rootView);
        return rootView;

    }

    public void setData(View view){
        if(view.findViewById(R.id.recycler_view) != null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mAdapter = new ItineraryAdapter(itenList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

            recyclerView.addOnItemTouchListener(new ItineraryCatalog().getTouchListener(recyclerView,this.getContext()));
        }
    }



}
