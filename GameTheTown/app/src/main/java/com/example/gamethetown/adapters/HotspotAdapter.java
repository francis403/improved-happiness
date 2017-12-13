package com.example.gamethetown.adapters;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.ItemTouchHelperAdapter;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class HotspotAdapter extends RecyclerView.Adapter<HotspotAdapter.MyViewHolder>
implements ItemTouchHelperAdapter{

    private List<Hotspot> hotspotList;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Hotspot hot = hotspotList.remove(fromPosition);
        hotspotList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, hot);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        hotspotList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //a pensar ter um floating button que te deixe adicionar ou remover
        public TextView title,gameType;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            gameType = (TextView) view.findViewById(R.id.gameType);
            image = (ImageView) view.findViewById(R.id.iten_image);
        }
    }


    public HotspotAdapter(List<Hotspot> hotspotList) {
        this.hotspotList = hotspotList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotspot_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Hotspot hot = hotspotList.get(position);
        holder.title.setText(hot.getTitle());
        holder.gameType.setText(hot.getGame().getGameName());
        holder.image.setImageResource(hot.getImageID());

    }

    @Override
    public int getItemCount() {
        return hotspotList.size();
    }

    public int getTotalWalkingDistance(){
        int total = 0;

        for(int i  = 1 ; i < hotspotList.size(); i++){
            Location l = hotspotList.get(i-1).getLocation();
            Location l2 = hotspotList.get(i).getLocation();
            total += l.distanceTo(l2);
            l = l2;
        }
        return total;
    }

    public List<Hotspot> getList(){return Collections.unmodifiableList(hotspotList);}

}
