package com.example.collegebuddyversion2.Models;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("MobileNo")
    private String mobile_number;
    @SerializedName("Password")
    private String password;

    public LoginData(String mobile_number, String password) {
        this.mobile_number = mobile_number;
        this.password = password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getPassword() {
        return password;
    }
}
