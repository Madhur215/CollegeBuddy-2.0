package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import com.example.collegebuddyversion2.Activities.ViewAnswersActivity;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAnswerRepository {

    private JsonApiHolder jsonApiHolder;
    private Application application;

    public PostAnswerRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
    }

    public void AddAnswer(String answer){
        Call<ResponseBody> call = jsonApiHolder.addAnswer(ViewAnswersActivity.question_id,
                prefUtils.getToken() , answer);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(application, "Answer Added!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
