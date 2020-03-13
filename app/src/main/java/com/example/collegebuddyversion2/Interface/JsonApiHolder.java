package com.example.collegebuddyversion2.Interface;

import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.Models.EditDetails;
import com.example.collegebuddyversion2.Models.LoginData;
import com.example.collegebuddyversion2.Models.LoginResponse;
import com.example.collegebuddyversion2.Models.Members;
import com.example.collegebuddyversion2.Models.Profile;
import com.example.collegebuddyversion2.Models.QuestionsResponse;
import com.example.collegebuddyversion2.Models.SignUpData;
import com.example.collegebuddyversion2.Models.SignUpResponse;
import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.Models.Subjects;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("Member/Profile")
    Single<List<Profile>> getProfile(@Query("token") String token);

    @Multipart
    @POST("Contacts/ImageUpload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody ,
                                   @Query("token") String token);

    @GET("Contact/QuestionTab")
    Single<List<QuestionsResponse>> getUserQuestions(@Query("token") String token);

    @POST("Member/EditProfile")
    Single<String> editProfile(@Query("token") String token , @Body EditDetails data );

    @POST("PDFController/AddToLibrary/{PKEY}")
    Call<String> addToLibrary(@Path("PKEY") int key , @Query("token") String token);

    @GET("PDFController/Library")
    Single<List<SubjectPdfList>> getLibrary(@Query("token") String token);

    @GET("Contact/List")
    Call<List<Members>> getMembers(@Query("token") String token);

    @GET("Contact/SubjectList")
    Single<List<Subjects>> getSubjects(@Query("token") String token);

    @GET("PDFcontroller/SubjectPDF")
    Call<List<SubjectPdfList>> getPdfs(@Query("token") String token , @Query("key") String key);
}
