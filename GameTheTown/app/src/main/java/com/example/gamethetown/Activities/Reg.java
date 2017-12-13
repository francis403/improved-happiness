package com.example.gamethetown.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gamethetown.Database.UserAuthentication;
import com.example.gamethetown.Database.UserDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.item.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Reg extends AppCompatActivity {

    @InjectView(R.id.button_register) Button but_register;
    @InjectView(R.id.text_input_name) TextInputLayout name;
    @InjectView(R.id.text_input_email) TextInputLayout email;
    @InjectView(R.id.text_input_password) TextInputLayout password_1;
    @InjectView(R.id.input_confirm_password) TextInputLayout password_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.inject(this);

        but_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String em = email.getEditText().getText().toString();
                final String pas_1 = password_1.getEditText().getText().toString();
                final String n = name.getEditText().getText().toString();
                if(validate()) {
                    new UserAuthentication().addUser(em, pas_1,n,Reg.this)
                    .addOnCompleteListener(Reg.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserAuthentication ua = new UserAuthentication();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Reg.this, "Registration successfull.",
                                        Toast.LENGTH_SHORT).show();

                                Task upd = ua.updateUserName(n); //meter isto para traz
                                while(!upd.isComplete()){}
                                User created = new User(ua.getUser().getUid(),n);
                                ua.setCurrentUser(created);

                                new UserDatabaseConnection(created.getUserID()).setUser(created);
                                Intent intent = new Intent(Reg.this,Profile.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Error with firebase", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Reg.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void goToLogin(View view){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public boolean validate() {
        boolean valid = true;

        String n = this.name.getEditText().getText().toString();
        String em = this.email.getEditText().getText().toString();
        String password1 = password_1.getEditText().getText().toString();
        String password2 = password_2.getEditText().getText().toString();

        if (n.isEmpty() || n.length() < 3 && n.length() > 15) {
            name.setError("between 3 and 15 characters");
            valid = false;
        } else
            name.setError(null);

        if (em.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else
            email.setError(null);

        if (password1.isEmpty() || password1.length() < 6 || password1.length() > 15) {
            password_1.setError("between 6 and 15 alphanumeric characters");
            valid = false;
        } else
            password_1.setError(null);

        if (!password2.equals(password1)) {
            password_2.setError("Password is Different!");
            valid = false;
        } else
            password_2.setError(null);

        return valid;
    }

}
