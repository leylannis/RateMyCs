package com.example.ratemycs;

import java.io.Serializable;

// class to hold Course information
public class Course implements Serializable {
    public String code;
    public String name;
    public String school;

    public Course(String code, String name, String school) {
        this.code = code;
        this.name = name;
        this.school = school;
    }

    public Course(){
        this.code = "no code";
        this.name = "no name";
        this.school = "no school";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
