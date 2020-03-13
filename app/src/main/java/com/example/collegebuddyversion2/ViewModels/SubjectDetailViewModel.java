package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.Repository.SubjectDetailRepository;

import java.util.List;

public class SubjectDetailViewModel extends AndroidViewModel {

    private SubjectDetailRepository subjectDetailRepository;

    public SubjectDetailViewModel(@NonNull Application application) {
        super(application);
        subjectDetailRepository = new SubjectDetailRepository(application);
    }

    public LiveData<List<SubjectPdfList>> getPdfs(String pdf_key){
        return subjectDetailRepository.getPdfs(pdf_key);
    }


}
