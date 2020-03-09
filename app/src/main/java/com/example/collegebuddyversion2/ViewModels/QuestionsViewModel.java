package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Entity.QuestionsEntity;
import com.example.collegebuddyversion2.Repository.QuestionsRepository;

import java.util.List;

public class QuestionsViewModel extends AndroidViewModel {

    private LiveData<List<QuestionsEntity>> questionsList;

    public QuestionsViewModel(@NonNull Application application) {
        super(application);
        QuestionsRepository questionsRepository = new QuestionsRepository(application);
        questionsList = questionsRepository.getQuestionsList();
    }

    public LiveData<List<QuestionsEntity>> getQuestionsList(){
        return questionsList;
    }
}
