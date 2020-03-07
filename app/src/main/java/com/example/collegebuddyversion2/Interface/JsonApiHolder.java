package com.example.collegebuddyversion2.Interface;

import com.example.collegebuddyversion2.Models.LoginData;
import com.example.collegebuddyversion2.Models.LoginResponse;
import com.example.collegebuddyversion2.Models.SignUpData;
import com.example.collegebuddyversion2.Models.SignUpResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApiHolder {

    @POST("Member/Login")
    Single<LoginResponse> login(@Body LoginData data);

    @POST("Member/Signup")
    Single<SignUpResponse> signUp(@Body SignUpData data);

    @POST("Member/Verify/{ID}")
    Single<String> verifyPhone(@Path("ID") String id , @Body String otp);




}
