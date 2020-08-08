package com.example.lms.ViewModels;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.SectionListener;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Repository.SectionRepository;
import com.example.lms.Repository.StudentRepository;

import retrofit2.Response;

public class SectionViewModel extends ViewModel implements SectionListener {
    MutableLiveData<Response<SectionResponse>> MutableLiveData;
    SectionRepository repository;
    String TAG = SectionViewModel.class.getSimpleName();

    public SectionViewModel(Context context, ProgressBar progressBar, String id) {
        MutableLiveData = new MutableLiveData<>();
        repository = new SectionRepository(context,progressBar,id);
        repository.setListener(this);
    }

    public void update(Context context,ProgressBar progressBar,String id){
        repository = new SectionRepository(context,progressBar,id);
        repository.setListener(this);
    }

    public androidx.lifecycle.MutableLiveData<Response<SectionResponse>> getMutableLiveData() {
        return MutableLiveData;
    }

    @Override
    public void onSuccess(Response<SectionResponse> response) {
        MutableLiveData.setValue(response);
    }
}
