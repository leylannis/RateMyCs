package com.example.ratemycs;

// class to store Review element data
public class Review {
    String code, description, professor, score;

    public Review(String code, String description, String professor, String score) {
        this.code = code;
        this.description = description;
        this.professor = professor;
        this.score = score;
    }

    public Review() {
        this.code = "none";
        this.description = "none";
        this.professor = "none";
        this.score = "none";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
