package com.example.ratemycs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<Course> coursesArray;
    MyFilter filter;
    AdapterView.OnItemClickListener listener;

    public CourseAdapter(Context context, ArrayList<Course> coursesArray) {
        this.context = context;
        this.coursesArray = coursesArray;
        filter = new MyFilter(coursesArray, this);
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

    public void setList(ArrayList<Course> newList){
        this.coursesArray = newList;
        notifyDataSetChanged();
    }

    public void filterList(String text) {
        filter.filter(text);
    }

    @Override
    public void onClick(View v) {
        // nyi
    }
}
