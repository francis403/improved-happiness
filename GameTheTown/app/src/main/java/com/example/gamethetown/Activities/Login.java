package com.example.gamethetown.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.Database.UserAuthentication;
import com.example.gamethetown.Database.UserDatabaseConnection;
import com.example.gamethetown.Dialogs.AlertDialogs.LoadingDialog;
import com.example.gamethetown.R;
import com.example.gamethetown.item.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import butterknife.InjectView;

public class Login extends AppCompatActivity {

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.button_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    /**
     * Start user query
     * if query is successfull login
     * else no login
     * @param view
     */
    public void onLoginPress(View view){

        if(new UserAuthentication().getUser() != null) {
            Log.e("LogedIN", "User is still logged in");
            Log.e("Name",new UserAuthentication().getUser().getDisplayName());
        }

        //Check if everything is fine
        if(validate()) {
            final LoadingDialog ld = new LoadingDialog(
                    Login.this,getString(R.string.loading_user));
            ld.show();
            Task<AuthResult> task = new UserAuthentication()
                    .loginCheck(_emailText.getText().toString(),
                    _passwordText.getText().toString(),null);

            task.addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.e("Was successful","Login was successful");
                        // Sign in success, update UI with the signed-in user's information
                        String userID = new UserAuthentication().getUser().getUid();
                        DatabaseReference mUserRef = new UserDatabaseConnection
                                (userID).getReference().child(userID);
                        //vamos buscar os valores do user
                        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //o login foir feito
                                ld.dismiss();
                                User tst = new User(dataSnapshot);
                                new UserAuthentication().setCurrentUser(tst);
                                Intent intent = new Intent(Login.this, Profile.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                ld.dismiss();
                            }
                        });
                    } else {
                        ld.hide();
                        // If sign in fails, display a message to the user.
                        Log.w("FB:Login exception", "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                }
            });

        }
    }

    public void goToRegister(View view){
        Intent intent = new Intent(this,Reg.class);
        startActivity(intent);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
