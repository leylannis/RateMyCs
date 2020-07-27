package com.example.ratemycs;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class MyFilter extends Filter {

    private ArrayList<Course> courseList;
    private ArrayList<Course> filteredCourseList;
    private CourseAdapter adapter;

    public MyFilter(ArrayList<Course> courseList, CourseAdapter adapter) {
        this.adapter = adapter;
        this.courseList = courseList;
        this.filteredCourseList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredCourseList.clear();
        final FilterResults results = new FilterResults();

        if (constraint == null || constraint.length() == 0) {
            results.count = filteredCourseList.size();
            results.values = filteredCourseList;

        } else {
            // here you need to add proper items to filteredCourseList
            for (final Course item : courseList) {
                if (item.getName().toLowerCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                    filteredCourseList.add(item);
                }
            }
            results.values = filteredCourseList;
            results.count = filteredCourseList.size();
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredCourseList);
        adapter.notifyDataSetChanged();
    }
}
