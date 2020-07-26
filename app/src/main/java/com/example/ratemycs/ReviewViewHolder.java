package com.example.ratemycs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder{

    TextView descView, profView, scoreView;
    public View itemView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        descView = (TextView)itemView.findViewById(R.id.description_TextView);
        profView = (TextView)itemView.findViewById(R.id.professor_TextView);
        scoreView = (TextView)itemView.findViewById(R.id.score_TextView);
    }

    public void setData(Review review) {
        descView.append(review.getDescription());
        profView.append("Professor Taken: " + review.getProfessor());
        scoreView.setText("Score: " + review.getScore());
    }
}