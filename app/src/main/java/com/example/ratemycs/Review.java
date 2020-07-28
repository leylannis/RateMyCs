package com.example.ratemycs;

// class to store Review element data
public class Review {
    String code, description, professor, score, creator;

    public Review(String code, String description, String professor, String score, String creator) {
        this.code = code;
        this.description = description;
        this.professor = professor;
        this.score = score;
        this.creator = creator;
    }

    public Review() {
        this.code = "none";
        this.description = "none";
        this.professor = "none";
        this.score = "none";
        this.creator = "none";
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
