package com.example.ratemycs;

import java.util.ArrayList;

// user class
public class User {
    private String email;
    private int reviewCount;
    private ArrayList<String> savedCourses;

    public User() {
        this.email = "";
        this.reviewCount = 0;
        this.savedCourses = new ArrayList<>();
    }

    public User(String email, int reviewCount, ArrayList<String> savedCourses) {
        this.email = email;
        this.reviewCount = reviewCount;
        this.savedCourses = savedCourses;
    }

    public User(String email) {
        this.email = email;
        this.reviewCount = 0;
        this.savedCourses = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ArrayList<String> getSavedCourses() {
        return savedCourses;
    }

    public void setSavedCourses(ArrayList<String> savedCourses) {
        this.savedCourses = savedCourses;
    }

    public void saveCourse(String newCode){
        this.savedCourses.add(newCode);
    }
}

