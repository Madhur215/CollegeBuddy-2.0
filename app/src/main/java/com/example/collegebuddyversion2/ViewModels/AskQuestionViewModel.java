package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Repository.AskQuestionRepository;

public class AskQuestionViewModel extends AndroidViewModel {

    private AskQuestionRepository askQuestionRepository;

    public AskQuestionViewModel(@NonNull Application application) {
        super(application);
        askQuestionRepository = new AskQuestionRepository(application);

    }

    public void PostQuestion(String Question){
        askQuestionRepository.PostQuestion(Question);
    }
}
