package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegebuddyversion2.Activities.ViewAnswersActivity;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersRepository {

    private JsonApiHolder jsonApiHolder;
    private Application application;
    private MutableLiveData<List<Answers>> answersDataList = new MutableLiveData<>();

    public AnswersRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
        getAnswers(ViewAnswersActivity.question_id);
    }

    private void getAnswers(String question_id){

        Call<List<Answers>> call = jsonApiHolder.getAnswers(question_id , prefUtils.getToken());
        call.enqueue(new Callback<List<Answers>>() {
            @Override
            public void onResponse(Call<List<Answers>> call, Response<List<Answers>> response) {
                if(response.isSuccessful()) {
                    List<Answers> ansList = new ArrayList<>();
                    try {
                        List<Answers> answersList = response.body();

                        for (Answers ans : answersList) {

                            String name = ans.getAnswered_by_name();
                            String answer = ans.getAnswer();
                            String upvotes = ans.getUpvotes();

                            Answers show_answers = new Answers(name, answer, upvotes);
                            ansList.add(show_answers);
                        }
                        answersDataList.setValue(ansList);
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
                    }
                }

                else{
                    Toast.makeText(application, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Answers>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public LiveData<List<Answers>> getAnswersList(){
        return answersDataList;
    }

}
