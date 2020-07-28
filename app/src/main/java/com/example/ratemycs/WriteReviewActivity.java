package com.example.ratemycs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.graphics.Color.RED;

public class WriteReviewActivity extends AppCompatActivity {
    TextView courseCode;
    EditText scoreView, professorView, descriptionView;
    String code, score, professor, description;
    Button submit, clear;
    String creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        // initialize view elements
        courseCode = findViewById(R.id.code_TextView);
        scoreView = findViewById(R.id.score_EditText);
        professorView = findViewById(R.id.prof_EditText);
        descriptionView = findViewById(R.id.desc_EditText);
        submit = findViewById(R.id.submit_Button);
        clear = findViewById(R.id.clear_Button);

        creator = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // retrieve course code sent via intent
        String intentData = getIntent().getStringExtra("code");
        courseCode.setText(intentData);

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
                    addReviewToDatabase();
                }
            }
        });

    }

    private boolean checkInput(){
        // ensure valid rating number input
        if (!score.equals("1") && !score.equals("2") && !score.equals("3") && !score.equals("4") && !score.equals("5")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nRating must be 1-5", Toast.LENGTH_LONG).show();
            return false;
        }
        // professor name must be provided
        else if(professor.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nProfessor name is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        // a review must have a body
        else if(description.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nReview description is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void addReviewToDatabase(){
        // add review to node of reviews in firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child("reviews");
        mRef.push().setValue(new Review(code, description, professor, score, creator));
        Toast.makeText(getApplicationContext(), "Review Submitted Successfully", Toast.LENGTH_SHORT).show();
        ReviewAdapter.reviewsArray.add(new Review(code, description, professor, score, creator));
        finish();
    }
}