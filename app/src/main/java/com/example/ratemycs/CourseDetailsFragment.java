package com.example.ratemycs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseDetailsFragment extends Fragment {

    Course selected;
    TextView codeView, nameView, schoolView, avgView;
    Button reviewButton, saveButton;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    View v;
    ReviewAdapter adapter;

    private static ArrayList<Review> reviewElems;
    private List<String> mDataKey = new ArrayList<>();

    public CourseDetailsFragment() {
        // Required empty public constructor
    }

    public static CourseDetailsFragment newInstance(String param1, String param2) {
        return new CourseDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        selected = (Course) b.getSerializable("course");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this.v = view;
        init();
    }

    private void init() {
        codeView = getView().findViewById(R.id.code_details);
        nameView = getView().findViewById(R.id.name_details);
        schoolView = getView().findViewById(R.id.school_details);
        avgView = getView().findViewById(R.id.avgRating_details);

        reviewButton = getView().findViewById(R.id.reviewButton);
        saveButton = getView().findViewById(R.id.saveButton);
        recyclerView = getView().findViewById(R.id.review_Recycler);

        codeView.append(selected.getCode());
        nameView.append(selected.getName());
        schoolView.append(selected.getSchool());

        reviewElems = new ArrayList<>();

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteReviewActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) Objects.requireNonNull(getView()).findViewById(R.id.review_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewElems = new ArrayList<>();
        LoadReviews();
        adapter = new ReviewAdapter(getActivity(), reviewElems);
        recyclerView.setAdapter(adapter);
    }

    private void LoadReviews(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reviews");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewElems.clear();
                mDataKey.clear();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    reviewElems.add(single.getValue(Review.class));
                    mDataKey.add(single.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}