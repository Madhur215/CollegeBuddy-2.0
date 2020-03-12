package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class UserQuestionRepository {

    private MutableLiveData<List<Questions>> userQuestionsList = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private JsonApiHolder jsonApiHolder;
    private Application application;

    public UserQuestionRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
        getUserQuestions();

    }

    private void getUserQuestions(){

        disposable.add(
                jsonApiHolder.getUserQuestions(prefUtils.getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<QuestionsResponse>>(){

                            @Override
                            public void onSuccess(List<QuestionsResponse> questions) {
                                List<Questions> list = new ArrayList<>();
                                for (QuestionsResponse question : questions) {

                                    String q = question.getQuestion();
                                    String qId = question.getQuestion_id();
                                    String name = question.getAsked_by_name();
                                    String date = question.getAsked_on_date();
                                    String image = question.getImage();
                                    Questions qList = new Questions(q, qId, name, date, image);
                                    list.add(qList);
                                }
                                userQuestionsList.setValue(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }

    public LiveData<List<Questions>> getUserQuestionsList(){
        return userQuestionsList;
    }

}
