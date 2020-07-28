package com.example.ratemycs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Predicate;


public class ProfileFragment extends Fragment {

    TextView emailView, schoolView;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    String userEmail, userSchool;
    RecyclerView recycler;
    ArrayList<User> userElems = new ArrayList<>();
    ArrayList<Review> reviewElems = new ArrayList<>();
    ReviewAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hooks to view elements
        emailView = getView().findViewById(R.id.currentUser_Email);
        schoolView = getView().findViewById(R.id.currentUser_School);
        recycler = getView().findViewById(R.id.profileReview_Recycler);

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        mRef = FirebaseDatabase.getInstance().getReference().child("users");


        emailView.setText(userEmail);
        schoolView.setText(userSchool);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReviewAdapter(getActivity(), reviewElems);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LoadUsers();
        LoadReviews();
    }

    private void LoadUsers() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // add database review items to list
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    userElems.add(single.getValue(User.class));
                }

                // filter list to include only current user
                userElems.removeIf(new Predicate<User>() {
                    @Override
                    public boolean test(User user) {
                        return (!user.getEmail().equals(userEmail));
                    }
                });
                schoolView.setText(userElems.get(0).getSchool());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LoadReviews() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reviews");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewElems.clear();
                // add database review items to list
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    reviewElems.add(single.getValue(Review.class));
                }

                // filter list to include only selected course's reviews
                reviewElems.removeIf(new Predicate<Review>() {
                    @Override
                    public boolean test(Review n) {
                        return (!n.getCreator().equals(userEmail));
                    }
                });
                    adapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}