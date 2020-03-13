package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Models.Subjects;
import com.example.collegebuddyversion2.Repository.SubjectsRepository;

import java.util.List;

public class SubjectsViewModel extends AndroidViewModel {

    private LiveData<List<Subjects>> subjectsList;
    private SubjectsRepository subjectsRepository;

    public SubjectsViewModel(@NonNull Application application) {
        super(application);
        subjectsRepository = new SubjectsRepository(application);

    }

    public LiveData<List<Subjects>> getSubjects(){
        return subjectsRepository.getSubjects();
    }
}
