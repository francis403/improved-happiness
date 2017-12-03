package com.example.gamethetown.Catalogs;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gamethetown.item.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserAuthentication {

    private static FirebaseAuth auth;

    //ideia -> usamos isto e metemos um construtor que vai buscar os dados todos da bd
    private static User currentUser;

    public UserAuthentication(){
        if(auth == null) auth = FirebaseAuth.getInstance();
    }

    public void setCurrentUser(User user){this.currentUser = user;}
    public User getCurrentUser(){return currentUser;}


    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    // todo -> precisa de adicionar fotos
    /**
     * @requires valid passoword and email
     * @param email -> email given to add the user
     * @param password -> password given of the user to add
     * @return task to do it with what we please
     */
    public Task<AuthResult> addUser(String email, String password, String name,
                              final Activity activity) {
        Task<AuthResult> task = auth.createUserWithEmailAndPassword(email,password);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a storage reference from our app
        //Not sure if accurate
        return task;
    }

    //The user does what he pleases with the result
    public Task<AuthResult> loginCheck(String email, String password, final Activity activity){
        Task<AuthResult> task = auth.signInWithEmailAndPassword(email, password);
        return task;
    }

    public Task<Void> updateUserName(final String newName){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName).build();

        return user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.e("Firebase userName", "User profile updated. Name Given: " + newName);
                }
                else{
                    Log.e("Firebase userName error","Erro na criacao do nome: " + task.getException());
                }
            }
        });
    }

    public void setUserPhoto(Uri uri){
        FirebaseUser user = this.getUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.
                Builder().setPhotoUri(uri).build();
    }

    public void log_out(){
        FirebaseAuth.getInstance().signOut();
    }

}
