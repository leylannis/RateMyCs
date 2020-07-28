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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings("ALL")
public class CourseDetailsFragment extends Fragment {

    Course selected;
    TextView codeView, nameView, schoolView, avgView;
    Button reviewButton, saveButton;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    ReviewAdapter adapter;

    private static ArrayList<Review> reviewElems;
    private List<String> mDataKey = new ArrayList<>();

    public CourseDetailsFragment() {
        // Required empty public constructor
    }

    public static CourseDetailsFragment newInstance() {
        return new CourseDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        selected = (Course) Objects.requireNonNull(b).getSerializable("course");
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
        init();
    }

    private void init() {
        // initialize view elements
        codeView = Objects.requireNonNull(getView()).findViewById(R.id.code_details);
        nameView = getView().findViewById(R.id.name_details);
        schoolView = getView().findViewById(R.id.school_details);
        avgView = getView().findViewById(R.id.avgRating_details);
        reviewButton = getView().findViewById(R.id.reviewButton);
        saveButton = getView().findViewById(R.id.saveButton);
        recyclerView = getView().findViewById(R.id.review_Recycler);

        // add course details to view
        codeView.append(selected.getCode());
        nameView.append(selected.getName());
        schoolView.append(selected.getSchool());

        reviewElems = new ArrayList<>();

        // start WriteReviewActivity to write a review on course being viewed
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.currentUser == null)
                    Toast.makeText(getContext(), "Sign in to Leave a Review", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getActivity(), WriteReviewActivity.class);
                    intent.putExtra("code", selected.getCode());
                    CourseDetailsFragment.super.onPause();
                    startActivity(intent);
                    // to update view with newly added review
                    CourseDetailsFragment.super.onResume();
                }
            }
        });

        // add current course to list of saved elements
        // can now be seen in "Saved Courses" tab of menu
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.currentUser == null)
                    Toast.makeText(getContext(), "Sign in to Save a Course", Toast.LENGTH_SHORT).show();
                else{
                    if(!SavedFragment.savedElems.contains(selected))
                        SavedFragment.savedElems.add(selected);
                    else
                        Toast.makeText(getContext(),"Course is already saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // setup recyclerview to be updated with relevant reviews
        recyclerView = (RecyclerView) Objects.requireNonNull(getView()).findViewById(R.id.review_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewElems = new ArrayList<>();
        LoadReviews();
        adapter = new ReviewAdapter(getActivity(), reviewElems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void LoadReviews() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reviews");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewElems.clear();
                mDataKey.clear();
                // add database review items to list
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    reviewElems.add(single.getValue(Review.class));
                    mDataKey.add(single.getKey());
                }

                // filter list to include only selected course's reviews
                reviewElems.removeIf(new Predicate<Review>() {
                    @Override
                    public boolean test(Review n) {
                        return (!n.getCode().equals(selected.getCode()));
                    }
                });

                // calculate and add average rating score to view
                if (reviewElems.size() > 0) {
                    int avgSum = 0;
                    int count = 0;
                    for (int i = 0; i < reviewElems.size(); i++) {
                        avgSum += Integer.parseInt(reviewElems.get(i).getScore());
                        count++;
                    }
                    double avg = avgSum / count;
                    avgView.setText(String.valueOf(avg));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadReviews();
        adapter.notifyDataSetChanged();
    }
}