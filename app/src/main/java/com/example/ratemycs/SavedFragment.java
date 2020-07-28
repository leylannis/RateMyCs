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

// fragment that shows courses saved by user
// session specific (no persistence)
public class SavedFragment extends Fragment {
    public static ArrayList<Course> savedElems = new ArrayList<>();
    private CourseAdapter adapter;
    private RecyclerView recyclerView;
    TextView none1, none2;

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
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialize view element references
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_View);
        none2 = getView().findViewById(R.id.noneYet_TextView2);

        // hide recyclerview and display error message if there are no saved courses
        if(savedElems.size() <= 0)
            recyclerView.setVisibility(View.INVISIBLE);
        else
            init();
    }

    private void init() {
        // hide error messages
        none2.setVisibility(View.INVISIBLE);

        // setup recyclerview to display saves courses
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CourseAdapter(getActivity(), savedElems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}