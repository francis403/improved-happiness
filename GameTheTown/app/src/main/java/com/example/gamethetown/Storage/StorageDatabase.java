package com.example.gamethetown.Storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gamethetown.Database.DBConstants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * A way of communicating with the Storage in Firebase
 */
public class StorageDatabase {

    private StorageReference imagesRef;
    private StorageReference itensRef;

    private Bitmap helper;

    public StorageDatabase(){
        imagesRef = FirebaseStorage.getInstance().getReference().child("images");
        itensRef = imagesRef.child(DBConstants.REFERENCE_ITINERARIES);
    }

    private UploadTask addPhoto(Bitmap image,StorageReference strRef){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        return strRef.putBytes(data);
    }

    /**
     * Adds a photo to a specific itinerary
     * @param itenID -> The itinerary ID of the itinerary we want to set the photo
     * @param image -> the image to set there
     * @return
     */
    public UploadTask setItenPhoto(String itenID, Bitmap image){
        StorageReference photo_ref = itensRef.child(itenID).child("photo");
        return addPhoto(image,photo_ref);
    }

    public UploadTask setHotspotPhoto(String itenID,int hotspot_i, Bitmap image){
        StorageReference photo_ref = itensRef.child(itenID)
                .child(DBConstants.REFERENCE_HOTSPOTS).child(""+hotspot_i);
        return addPhoto(image,photo_ref);
    }


    public Task getItenPhoto(String itenID){
        return itensRef.child(itenID).child("photo").getDownloadUrl();
    }

    public Task getHotspotPhoto(String itenID,int hotspot_i){
        return itensRef.child(itenID).child(DBConstants.REFERENCE_HOTSPOTS)
                .child(""+hotspot_i).getDownloadUrl();
    }

}
