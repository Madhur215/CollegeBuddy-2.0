package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegebuddyversion2.Entity.QuestionsEntity;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Questions;
import com.example.collegebuddyversion2.Models.QuestionsResponse;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsRepository {

    private MutableLiveData<List<Questions>> questionsList = new MutableLiveData<>();
    private JsonApiHolder jsonApiHolder;
    private Application application;

    public QuestionsRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
        getQuestions();

    }

    private void getQuestions() {
//        disposable.add(
//                jsonApiHolder.getQuestions(prefUtils.getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<QuestionsResponse>>(){
//
//                    @Override
//                    public void onSuccess(List<QuestionsResponse> questionsResponses) {
//                        try {
//
//                            List<QuestionsEntity> questionsEntityList = new ArrayList<>();
//                            if (questionsResponses != null) {
//                                for (QuestionsResponse question : questionsResponses) {
//
//                                    String ques = question.getQuestion();
//                                    String asked_by_name = question.getAsked_by_name();
//                                    String question_id = question.getQuestion_id();
//                                    String date = question.getAsked_on_date();
//                                    String image = question.getImage();
//
//                                    QuestionsEntity questionsEntity = new QuestionsEntity(ques,
//                                            question_id, asked_by_name, date, image);
//                                    questionsEntityList.add(questionsEntity);
//
//                                }
//                                questionsDAO.insert(questionsEntityList);
//                            }
//                        }
//                        catch (NullPointerException e){
//                            Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        );
        Call<List<QuestionsResponse>> call = jsonApiHolder.getQuestions(prefUtils.getToken());
        call.enqueue(new Callback<List<QuestionsResponse>>() {
            @Override
            public void onResponse(Call<List<QuestionsResponse>> call,
                                   Response<List<QuestionsResponse>> response) {
                if (response.isSuccessful()) {
                    List<Questions> quesList = new ArrayList<>();
                    try {

                        List<QuestionsResponse> questions = response.body();

                        for (QuestionsResponse question : questions) {

                            String q = question.getQuestion();
                            String qId = question.getQuestion_id();
                            String name = question.getAsked_by_name();
                            String date = question.getAsked_on_date();
                            String image = question.getImage();
                            Questions qList = new Questions(q, qId, name, date, image);
                            quesList.add(qList);
                        }
                        questionsList.setValue(quesList);
                    } catch (NullPointerException e) {
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
                    }

                } else {
                    try {
                        Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();

                    } catch (NullPointerException e) {
                        Log.d(String.valueOf(e), "onResponse: TOAST");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuestionsResponse>> call, Throwable t) {
                try {
                    Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(String.valueOf(e), "onFailure: GET CONTEXT");
                }
            }
        });



    }

    public LiveData<List<Questions>> getQuestionsList(){
        return questionsList;
    }
}
