package com.example.gamethetown.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends App_Menu {

    @InjectView(R.id.name) TextView _nameText;
    @InjectView(R.id.level) TextView _levelText;
    @InjectView(R.id.profilePic) CircleImageView _profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        user.setImageID(R.drawable.user_photo); //test with a user photo

        _nameText.setText("Name: " + user.getName());
        _levelText.setText("Level: " + user.getLevel());
        _profileImage.setImageResource(user.getImageID());
    }

    public void checkCreatedIten(View view){
        Intent intent = new Intent(this,ListOfCreatedItineraries.class);
        startActivity(intent);
    }

    public void checkCompletedIten(View view){
        Intent intent = new Intent(this,ListOfCompletedItineraries.class);
        startActivity(intent);
    }

}
