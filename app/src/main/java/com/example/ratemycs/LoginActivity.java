package com.example.ratemycs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button signupButton, loginButton;
    TextView guestText;
    EditText emailField, passwordField;
    String email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize view elements and firebase auth
        mAuth = FirebaseAuth.getInstance();
        signupButton = findViewById(R.id.signup_Button);
        guestText = findViewById(R.id.continue_tag);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_Button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                if (checkFields()) {
                    // authenticate against authentication records
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                        MainActivity.email = email;
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Login failed! Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                if (checkFields()) {
                    // add to users in firebase authentication
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Signup successful!", Toast.LENGTH_LONG).show();

                                        // add user to users branch in realtime database
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference mRef = database.getReference().child("users");
                                        mRef.push().setValue(new User(email));
                                        // save email locally for later
                                        MainActivity.email = email;

                                        // start MainActivity for main app functionality
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Signup failed! Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                }
            }
        });

        guestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // continue to main app functionality with restricted access as a guest user
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean checkFields() {
        // check if login/signup inputs are valid
        if (email == null){
            Toast.makeText(getApplicationContext(),"Invalid Email or Password", Toast.LENGTH_SHORT).show();
            return false;}
        if (password == null || password.length() < 6){
            Toast.makeText(getApplicationContext(),"Invalid Email or Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}