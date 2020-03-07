package com.example.collegebuddyversion2.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String auth_token;

    public LoginResponse(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getAuth_token() {
        return auth_token;
    }
}
