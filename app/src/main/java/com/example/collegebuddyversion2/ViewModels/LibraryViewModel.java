package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;
import android.text.style.AlignmentSpan;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.Repository.LibraryRepository;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {

    private LibraryRepository libraryRepository;

    public LibraryViewModel(@NonNull Application application) {
        super(application);
        libraryRepository = new LibraryRepository(application);
    }

    public LiveData<List<SubjectPdfList>> getLibrary(){
        return libraryRepository.getLibrary();
    }
}
