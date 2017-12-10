package com.example.gamethetown.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Catalogs.UserAuthentication;
import com.example.gamethetown.Storage.ImageUserGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends App_Menu {

    private static final int PICK_FROM_FILE = 1;

    private ImagePicker imgPicker;

    @InjectView(R.id.loading) RelativeLayout loader;
    @InjectView(R.id.name) TextView _nameText;
    @InjectView(R.id.level) TextView _levelText;
    @InjectView(R.id.profilePic) CircleImageView _profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        //String name = new UserAuthentication().getUser().getDisplayName();
        _nameText.setText("Name: " + user.getName());
        _levelText.setText("Level: " + user.getLevel());

        StorageDatabase sd = new StorageDatabase();
        sd.getUserPhoto(user.getUserID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri o) {
                ImageUserGetter iug = new ImageUserGetter(_profileImage,loader);
                iug.execute(o.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                _profileImage.setImageResource(user.getImageID());
                loader.setVisibility(View.GONE);
            }
        });


        imgPicker = ImagePicker.create(this).returnAfterFirst(true)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")
                .enableLog(false);

    }

    public void getImage(View view){
        imgPicker.start(PICK_FROM_FILE);
    }

    public void checkCreatedIten(View view){
        Intent intent = new Intent(Profile.this,ListOfCreatedItineraries.class);
        startActivity(intent);
    }

    public void checkCompletedIten(View view){
        Intent intent = new Intent(Profile.this,ListOfCompletedItineraries.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK)
            return;

        // Check which request we're responding to
        if(requestCode == PICK_FROM_FILE){
            String path = "";
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Image image = images.get(0);
            path = image.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            new StorageDatabase().setUserPhoto(user.getUserID(), bitmap);
            //new UserAuthentication().setUserPhoto();
            _profileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onResume(){
        //caso tenha subido de nivel
        _levelText.setText(getString(R.string.Level) + " " + user.getLevel());
        super.onResume();
    }

}
