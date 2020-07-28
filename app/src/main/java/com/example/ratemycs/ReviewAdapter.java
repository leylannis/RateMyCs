package com.example.ratemycs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

// custom adapter to populate a recyclerview with a list of Reviews
public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder>  {
    private Context mContext;
    static ArrayList<Review> reviewsArray;

    public ReviewAdapter(Context context, ArrayList<Review> reviewsArray) {
        this.mContext = context;
        this.reviewsArray = reviewsArray;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // attach review item specific layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        final Review review = reviewsArray.get(position);
        holder.setData(review);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Review selectedReview = new Review(review.getCode(), review.getDescription(), review.getProfessor(), review.getScore(), review.getCreator());
                HomeFragment.selectedReview = selectedReview;
                Fragment fragment = new EditReviewFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("review", (Serializable) selectedReview);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewsArray.size();
    }
}