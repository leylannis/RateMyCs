package com.example.ratemycs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class WriteReviewActivity extends AppCompatActivity {
    TextView courseCode;
    EditText scoreView, professorView, descriptionView;
    String code, score, professor, description;
    Button submit, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        courseCode = findViewById(R.id.code_TextView);
        scoreView = findViewById(R.id.score_EditText);
        professorView = findViewById(R.id.prof_EditText);
        descriptionView = findViewById(R.id.desc_EditText);
        submit = findViewById(R.id.submit_Button);
        clear = findViewById(R.id.clear_Button);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreView.setText("");
                professorView.setText("");
                descriptionView.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // assign input values
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
        if (!score.equals("1") && !score.equals("2") && !score.equals("3") && !score.equals("4") && !score.equals("5")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nRating must be 1-5", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(professor.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nProfessor name is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(description.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter valid data for above fields\nReview description is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void addReviewToDatabase(){


    }
}