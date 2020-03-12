package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Models.Questions;
import com.example.collegebuddyversion2.Repository.UserQuestionRepository;

import java.util.List;

public class UserQuestionViewModel extends AndroidViewModel {

    private LiveData<List<Questions>> userQuestionList;

    public UserQuestionViewModel(@NonNull Application application) {
        super(application);
        UserQuestionRepository userQuestionRepository = new UserQuestionRepository(application);
        userQuestionList = userQuestionRepository.getUserQuestionsList();
    }

    public LiveData<List<Questions>> getUserQuestionList(){
        return userQuestionList;
    }
}
