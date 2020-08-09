package com.example.lms.ViewModels;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.StudentListener;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.UserData_EnrollmentHistoryModel;
import com.example.lms.Repository.StudentRepository;

import java.util.List;

import retrofit2.Response;

public class StudentViewModel extends ViewModel implements StudentListener {
    public MutableLiveData<List<UserData_EnrollmentHistoryModel>> MutableLiveData;
    StudentRepository studentRepository;
    String TAG = StudentViewModel.class.getSimpleName();

    public StudentViewModel(Context context, ProgressBar progressBar) {
        MutableLiveData = new MutableLiveData<>();
        studentRepository = new StudentRepository(context,progressBar);
        studentRepository.setStudentListener(this);
    }

    public void update(Context context,ProgressBar progressBar){
        studentRepository = new StudentRepository(context,progressBar);
        studentRepository.setStudentListener(this);
    }

    public androidx.lifecycle.MutableLiveData<List<UserData_EnrollmentHistoryModel>> getMutableLiveData() {
        return MutableLiveData;
    }

    @Override
    public void onSuccess(List<UserData_EnrollmentHistoryModel> list) {
        MutableLiveData.setValue(list);
    }
}
