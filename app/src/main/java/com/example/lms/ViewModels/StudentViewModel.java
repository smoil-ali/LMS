package com.example.lms.ViewModels;

import android.content.Context;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.StudentListener;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.StudentData;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Repository.CategoryRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;
import com.example.lms.Repository.StudentRepository;

import java.util.List;

import retrofit2.Response;

public class StudentViewModel extends ViewModel implements StudentListener {
    public MutableLiveData<Response<StudentResponse>> MutableLiveData;
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

    public androidx.lifecycle.MutableLiveData<Response<StudentResponse>> getMutableLiveData() {
        return MutableLiveData;
    }

    @Override
    public void onSuccess(Response<StudentResponse> response) {
        MutableLiveData.setValue(response);
    }
}
