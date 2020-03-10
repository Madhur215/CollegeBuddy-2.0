package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Repository.PostAnswerRepository;

public class PostAnswerViewModel extends AndroidViewModel {

    private PostAnswerRepository postAnswerRepository;

    public PostAnswerViewModel(@NonNull Application application) {
        super(application);
        postAnswerRepository = new PostAnswerRepository(application);
    }

    public void AddAnswer(String answer){
        postAnswerRepository.AddAnswer(answer);
    }
}
