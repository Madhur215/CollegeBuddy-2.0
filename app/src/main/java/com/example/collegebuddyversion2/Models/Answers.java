package com.example.collegebuddyversion2.Models;

import com.google.gson.annotations.SerializedName;

public class Answers {

    @SerializedName("Name")
    private String answered_by_name;
    @SerializedName("Answer")
    private String answer;
    @SerializedName("Upvotes")
    private String upvotes;

    public Answers(String name, String answer, String upvotes) {
        this.answered_by_name = name;
        this.answer = answer;
        this.upvotes = upvotes;
    }

    public String getAnswered_by_name() {
        return answered_by_name;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUpvotes() {
        return upvotes;
    }
}
