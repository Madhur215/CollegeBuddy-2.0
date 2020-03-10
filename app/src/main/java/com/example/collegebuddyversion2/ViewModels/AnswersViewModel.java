package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.Repository.AnswersRepository;

import java.util.List;

public class AnswersViewModel extends AndroidViewModel {

    private LiveData<List<Answers>> answersList;

    public AnswersViewModel(@NonNull Application application) {
        super(application);
        AnswersRepository answersRepository = new AnswersRepository(application);
        answersList = answersRepository.getAnswersList();
    }

    public LiveData<List<Answers>> getAnswersList(){
        return answersList;
    }
}
