package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Subjects;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SubjectsRepository {

    private MutableLiveData<List<Subjects>> subjectsList = new MutableLiveData<>();
    private JsonApiHolder jsonApiHolder;
    private Application application;
    private CompositeDisposable disposable = new CompositeDisposable();

    public SubjectsRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
    }

    public LiveData<List<Subjects>> getSubjects(){
        disposable.add(
                jsonApiHolder.getSubjects(prefUtils.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Subjects>>(){

                    @Override
                    public void onSuccess(List<Subjects> subjects) {
                        subjectsList.setValue(subjects);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
        );
        return subjectsList;
    }

}
