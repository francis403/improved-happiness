package com.example.gamethetown.adapters;

/**
 * Created by franc on 29/10/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.MyViewHolder> {

    private List<Itinerary> itineraryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView image;
        public int numHospots;
        public TextView dif;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.iten_image);
            dif = (TextView) view.findViewById(R.id.dif);
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(iten.getCreationDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        holder.date.setText("Created: " + day + "/" + month + "/" + year);
        holder.dif.setText("Difficulty: " + iten.getDifficulty());
        Bitmap bit;
        if((bit = iten.getImageBitmap()) != null){

        }
        String path = iten.getImagePath();
        if(path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.image.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        else
            holder.image.setImageResource(iten.getImageID());

    }

    @Override
    public int getItemCount() {
        return itineraryList.size();
    }
}
