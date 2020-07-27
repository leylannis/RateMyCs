package com.example.ratemycs;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.AdapterView;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    View v;
    private CourseAdapter adapter;
    private static ArrayList<Course> courseElems;
    private List<String> mDataKey = new ArrayList<>();
    SearchView searchview;
    static Course selectedCourse;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();

        searchview = Objects.requireNonNull(getView()).findViewById(R.id.searchView);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                // filter list to include only selected course reviews
                courseElems.removeIf(new Predicate<Course>() {
                    @Override
                    public boolean test(Course n) {
                        return (!n.getCode().contains(query));
                    }
                });
                LoadCourses();
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                // filter list to include only selected course reviews
                courseElems.removeIf(new Predicate<Course>() {
                    @Override
                    public boolean test(Course n) {
                        return (!n.getCode().contains(newText));
                    }
                });
                LoadCourses();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    private void init() {
        recyclerView = (RecyclerView) Objects.requireNonNull(getView()).findViewById(R.id.courseListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courseElems = new ArrayList<>();
        LoadCourses();
        adapter = new CourseAdapter(getActivity(), courseElems);
        recyclerView.setAdapter(adapter);
    }

    private void LoadCourses() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("courses");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseElems.clear();
                mDataKey.clear();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    courseElems.add(single.getValue(Course.class));
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