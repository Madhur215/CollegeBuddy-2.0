package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SubjectDetailRepository {

    private JsonApiHolder jsonApiHolder;
    private Application application;
    private MutableLiveData<List<SubjectPdfList>> pdfList = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    
    public SubjectDetailRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
    }

    public LiveData<List<SubjectPdfList>> getPdfs(String pdf_key) {
        disposable.add(
                jsonApiHolder.getPdfs(prefUtils.getToken(), pdf_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<SubjectPdfList>>(){

                    @Override
                    public void onSuccess(List<SubjectPdfList> subjectPdfLists) {
                        pdfList.setValue(subjectPdfLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
        );
        return pdfList;
    }
}
