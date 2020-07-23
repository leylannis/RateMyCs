package com.example.ratemycs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    private Context context;
    private ArrayList<Course> coursesArray;

    public CourseAdapter(Context context, ArrayList<Course> coursesArray) {
        this.context = context;
        this.coursesArray = coursesArray;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = coursesArray.get(position);
        holder.setData(course);
    }

    @Override
    public int getItemCount() {
        return coursesArray.size();
    }
}
