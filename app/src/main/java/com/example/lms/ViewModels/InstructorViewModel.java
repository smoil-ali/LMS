package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.InstructorListener;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.StudentData;
import com.example.lms.Repository.InstructorRepository;
import com.example.lms.Repository.StudentRepository;

import java.util.List;

import retrofit2.Response;

public class InstructorViewModel extends ViewModel implements InstructorListener {
    public MutableLiveData<Response<InstructorResponse>> MutableLiveData;
    InstructorRepository repository;
    String TAG = InstructorViewModel.class.getSimpleName();

    public InstructorViewModel(Context context, ProgressBar progressBar) {
        MutableLiveData = new MutableLiveData<>();
        repository = new InstructorRepository(context,progressBar);
        repository.setListener(this);
    }

    public void update(Context context,ProgressBar progressBar){
        repository = new InstructorRepository(context,progressBar);
        repository.setListener(this);
    }

    public androidx.lifecycle.MutableLiveData<Response<InstructorResponse>> getMutableLiveData() {
        return MutableLiveData;
    }

    @Override
    public void onSuccess(Response<InstructorResponse> response) {
        MutableLiveData.setValue(response);
    }
}
