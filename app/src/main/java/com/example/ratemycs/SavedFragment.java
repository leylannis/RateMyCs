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
import java.util.ArrayList;
import java.util.Objects;


public class SavedFragment extends Fragment {
    public static ArrayList<Course> savedElems = new ArrayList<>();
    private CourseAdapter adapter;
    private RecyclerView recyclerView;
    TextView none1, none2;
    FirebaseAuth mAuth;


    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_View);
        none1 = getView().findViewById(R.id.noneYet_TextView1);
        none2 = getView().findViewById(R.id.noneYet_TextView2);

        if(savedElems.size() <= 0)
            recyclerView.setVisibility(View.INVISIBLE);
        else
            init();
    }

    private void init() {
        none1.setVisibility(View.INVISIBLE);
        none2.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CourseAdapter(getActivity(), savedElems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}