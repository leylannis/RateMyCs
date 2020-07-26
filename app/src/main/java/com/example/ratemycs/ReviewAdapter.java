package com.example.ratemycs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder>  {
    private Context mContext;
    private ArrayList<Review> reviewsArray;

    public ReviewAdapter(Context context, ArrayList<Review> reviewsArray) {
        this.mContext = context;
        this.reviewsArray = reviewsArray;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        final Review review = reviewsArray.get(position);
        holder.setData(review);
    }

    @Override
    public int getItemCount() {
        return reviewsArray.size();
    }
}