package com.example.ratemycs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    String email, password, school;
    EditText emailSignup, passwordSignup;
    AutoCompleteTextView schoolSignup;
    Button submit;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailSignup = findViewById(R.id.email_Signup);
        passwordSignup = findViewById(R.id.password_Signup);
        schoolSignup = (AutoCompleteTextView) findViewById(R.id.autocomplete_School);
        submit = findViewById(R.id.go_Button_Signup);

        // Get the string array
        String[] countries = getResources().getStringArray(R.array.School_Options);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        schoolSignup.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailSignup.getText().toString().trim();
                password = passwordSignup.getText().toString().trim();
                school = schoolSignup.getText().toString().trim();
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
                                        mRef.push().setValue(new User(email, school));
                                        // save email locally for later
                                        MainActivity.email = email;

                                        // start MainActivity for main app functionality
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
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
    }

    private boolean checkFields() {
        // check if signup inputs are valid
        if (email == null) {
            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}