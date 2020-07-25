package com.example.ratemycs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class CourseViewHolder extends RecyclerView.ViewHolder{

    private TextView courseCode, courseName, courseSchool;
    public View itemView;

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        courseCode = (TextView)itemView.findViewById(R.id.code_TextView);
        courseName = (TextView)itemView.findViewById(R.id.name_TextView);
        courseSchool = (TextView)itemView.findViewById(R.id.school_TextView);
    }

//    private Course getItem(int position) {
  //     return HomeFragment.getItem(position);
    //}

    public void setData(Course course) {
        courseCode.setText(course.getCode());
        courseName.setText(course.getName());
        courseSchool.setText(course.getSchool());
    }
}
