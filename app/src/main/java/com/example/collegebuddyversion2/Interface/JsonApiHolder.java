package com.example.collegebuddyversion2.Interface;

import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.Models.LoginData;
import com.example.collegebuddyversion2.Models.LoginResponse;
import com.example.collegebuddyversion2.Models.QuestionsResponse;
import com.example.collegebuddyversion2.Models.SignUpData;
import com.example.collegebuddyversion2.Models.SignUpResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiHolder {

    @POST("Member/Login")
    Single<LoginResponse> login(@Body LoginData data);

    @POST("Member/Signup")
    Single<SignUpResponse> signUp(@Body SignUpData data);

    @POST("Member/Verify/{ID}")
    Single<String> verifyPhone(@Path("ID") String id , @Body String otp);

    @POST("Member/ResendOtp/{ID}")
    Single<ResponseBody> resendOTP(@Path("ID") String ID);

    @GET("Dashboard/Home")
    Call<List<QuestionsResponse>> getQuestions(@Query("token") String token);

    @POST("Dashboard/AskQuestion")
    Call<ResponseBody> askQuestion(@Query("token") String token , @Body String question) ;

    @GET("Dashboard/QuestionDetails/{QID}")
    Call<List<Answers>> getAnswers(@Path("QID") String id , @Query("token") String token);

    @POST("Dashboard/AddAnswer/{QID}")
    Call<ResponseBody> addAnswer(@Path("QID") String id , @Query("token")  String token ,
                                 @Body String answer);
}
