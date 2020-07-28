package com.example.ratemycs;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// user class
public class User {
    private String email, school;
    private ArrayList<String> savedCourses;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference mRef = db.getReference().child("users");

    public User() {
        this.email = "";
        this.savedCourses = new ArrayList<>();
        this.school = "";
    }

    public User(String email, ArrayList<String> savedCourses, String school) {
        this.email = email;
        this.savedCourses = savedCourses;
        this.school = school;
    }

    public User(String email, String school) {
        this.email = email;
        this.savedCourses = new ArrayList<>();
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String newEmail) {
        this.email = newEmail;
        mRef = FirebaseDatabase.getInstance().getReference().child("users");

        mRef.orderByChild("email").equalTo(this.email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String key=datas.getKey();
                        mRef.child(key).child("email").setValue(newEmail);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<String> getSavedCourses() {
        return savedCourses;
    }

    public void setSavedCourses(ArrayList<String> savedCourses) {
        this.savedCourses = savedCourses;
    }

    public void saveCourse(String newCode){
        this.savedCourses.add(newCode);
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(final String newSchool) {
        this.school = newSchool;
        mRef = FirebaseDatabase.getInstance().getReference().child("users");

        mRef.orderByChild("email").equalTo(this.email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String key=datas.getKey();
                        mRef.child(key).child("school").setValue(newSchool);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

