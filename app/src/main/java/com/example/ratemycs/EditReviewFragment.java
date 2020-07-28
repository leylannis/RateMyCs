package com.example.ratemycs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;

public class EditReviewFragment extends Fragment {
    Review selected;
    TextView courseCode;
    EditText scoreView, professorView, descriptionView;
    String code, score, professor, description;
    Button submit, clear;
    String creator;

    public EditReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        selected = (Review) Objects.requireNonNull(b).getSerializable("review");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        creator = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear inputs on clear button click
                scoreView.setText("");
                professorView.setText("");
                descriptionView.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // assign input values & add new review to database on submit button click

                code = courseCode.getText().toString();
                score = scoreView.getText().toString();
                professor = professorView.getText().toString();
                description = descriptionView.getText().toString();
                if(checkInput()){
                    final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reviews");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot single : dataSnapshot.getChildren()) {
                                String key = single.getKey();
                                db.child(key).child("score").setValue(score);
                                db.child(key).child("professor").setValue(professor);
                                db.child(key).child("description").setValue(description);
                                Toast.makeText(getContext(), "Review Successfully Edited", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                // end fragment
            }
        });

    }


    private void init() {
        // initialize view elements
        courseCode = getView().findViewById(R.id.code_TextViewEdit);
        scoreView = getView().findViewById(R.id.score_EditTextEdit);
        professorView = getView().findViewById(R.id.prof_EditTextEdit);
        descriptionView = getView().findViewById(R.id.desc_EditTextEdit);
        submit = getView().findViewById(R.id.submit_ButtonEdit);
        clear = getView().findViewById(R.id.clear_ButtonEdit);

        // add course details to view
        courseCode.setText(selected.getCode());
        scoreView.setText(selected.getScore());
        descriptionView.setText(selected.getDescription());
        professorView.setText(selected.getProfessor());
    }

    private boolean checkInput(){
        // ensure valid rating number input
        if (!score.equals("1") && !score.equals("2") && !score.equals("3") && !score.equals("4") && !score.equals("5")){
            Toast.makeText(getContext(), "Please enter valid data for above fields\nRating must be 1-5", Toast.LENGTH_LONG).show();
            return false;
        }
        // professor name must be provided
        else if(professor.equals("")){
            Toast.makeText(getContext(), "Please enter valid data for above fields\nProfessor name is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        // a review must have a body
        else if(description.equals("")){
            Toast.makeText(getContext(), "Please enter valid data for above fields\nReview description is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}