package com.example.collegebuddyversion2.Models;

public class Questions {

    private String question;
    private String qid;
    private String name;
    private String date;
    private String image;

    public Questions(String question, String qid, String name , String date , String image) {
        this.question = question;
        this.qid = qid;
        this.name = name;
        this.date = date;
        this.image = image;
    }

    public Questions(String question, String qid, String name , String date) {
        this.question = question;
        this.qid = qid;
        this.name = name;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getQuestion() {
        return question;
    }

    public String getQid() {
        return qid;
    }

    public String getName() {
        return name;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
