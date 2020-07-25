package com.example.ratemycs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Objects;

public class CourseDetailsFragment extends Fragment {

    Course selected;
    TextView codeView, nameView, schoolView;

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

        codeView.append(selected.getCode());
        nameView.append(selected.getName());
        schoolView.append(selected.getSchool());


    }
}