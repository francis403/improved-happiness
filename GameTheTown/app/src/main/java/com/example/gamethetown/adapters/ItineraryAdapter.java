package com.example.gamethetown.adapters;

/**
 * Created by franc on 29/10/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;

import java.util.List;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.MyViewHolder> {

    private List<Itinerary> itineraryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.iten_image);
        }
    }


    public ItineraryAdapter(List<Itinerary> moviesList) {
        this.itineraryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itinerary_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Itinerary iten = itineraryList.get(position);
        holder.title.setText(iten.getTitle());
        holder.date.setText(iten.getCreationDate().toString());
        holder.image.setImageResource(iten.getImageID());

    }

    @Override
    public int getItemCount() {
        return itineraryList.size();
    }
}
