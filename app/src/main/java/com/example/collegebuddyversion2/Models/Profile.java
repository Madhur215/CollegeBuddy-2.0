package com.example.collegebuddyversion2.Models;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("Name")
    private String user_name;
    @SerializedName("College")
    private String college;
    @SerializedName("Branch")
    private String branch;
    @SerializedName("Year")
    private String year;
    @SerializedName("Image")
    private String imageUri;


    public Profile(String user_name, String college, String branch, String year , String imageUri) {
        this.user_name = user_name;
        this.college = college;
        this.branch = branch;
        this.year = year;
        this.imageUri = imageUri;
    }

    public Profile(){

    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCollege() {
        return college;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

}
