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

// custom adapter to populate a recyclerview with a list of Courses
public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>  {
    private Context mContext;
    private ArrayList<Course> coursesArray;
    MyFilter filter;
    String key;

    public CourseAdapter(Context context, ArrayList<Course> coursesArray) {
        this.mContext = context;
        this.coursesArray = coursesArray;
        filter = new MyFilter(coursesArray, this);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // attach course item specific layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, final int position) {
        final Course course = coursesArray.get(position);
        holder.setData(course);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course selectedCourse = new Course(course.getCode(), course.getName(), course.getSchool());
                // save course code and send to CourseDetailsFragment to load
                // selected courses information and reviews (if any)
                HomeFragment.selectedCourse = selectedCourse;
                Fragment fragment = new CourseDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("course", (Serializable) selectedCourse);
                bundle.putString("key", key);
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
        return coursesArray.size();
    }

    public void setList(ArrayList<Course> newList){
        this.coursesArray = newList;
        notifyDataSetChanged();
    }

    public void filterList(String text) {
        filter.filter(text);
    }
}
