package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.InstructorListener;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.StudentData;
import com.example.lms.Repository.InstructorRepository;
import com.example.lms.Repository.StudentRepository;

import java.util.List;

public class InstructorViewModel extends ViewModel implements InstructorListener {
    public MutableLiveData<List<InstructorData>> MutableLiveData;
    public MutableLiveData<String> errorMessage ;
    InstructorRepository repository;
    String TAG = InstructorViewModel.class.getSimpleName();

    public InstructorViewModel(Context context, ProgressBar progressBar) {
        MutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        repository = new InstructorRepository(context,progressBar);
        repository.setListener(this);
    }

    public void update(Context context,ProgressBar progressBar){
        repository = new InstructorRepository(context,progressBar);
        repository.setListener(this);
    }

    public androidx.lifecycle.MutableLiveData<List<InstructorData>> getMutableLiveData() {
        return MutableLiveData;
    }

    public androidx.lifecycle.MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onSuccess(List<InstructorData> instructorData) {
        Log.i(TAG,instructorData.size()+" instructor size");
        MutableLiveData.setValue(instructorData);
    }

    @Override
    public void onError(String error) {
        errorMessage.setValue(error);;
    }

}
