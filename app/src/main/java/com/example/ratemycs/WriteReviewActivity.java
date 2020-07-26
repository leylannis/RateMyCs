package com.example.ratemycs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WriteReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        TextView courseCode;
        final EditText scoreView, professorView, descriptionView;
        final String code, score, professor, description;
        Button submit, clear;

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
    }
}