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
import com.example.lms.Repository.EnrollmentHistoryRepository;
import com.example.lms.Repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends ViewModel implements StudentListener {
    public MutableLiveData<List<StudentData>> MutableLiveData;
    public MutableLiveData<String> errorMessage ;
    StudentRepository studentRepository;
    String TAG = StudentViewModel.class.getSimpleName();

    public StudentViewModel(Context context, ProgressBar progressBar) {
        MutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        studentRepository = new StudentRepository(context,progressBar);
        studentRepository.setStudentListener(this);
    }

    @Override
    public void onSuccess(List<StudentData> studentDataList) {
        Log.i(TAG,studentDataList.size()+" student size");
        MutableLiveData.setValue(studentDataList);
    }

    @Override
    public void onError(String error) {
        errorMessage.setValue(error);;
    }

    public androidx.lifecycle.MutableLiveData<List<StudentData>> getMutableLiveData() {
        return MutableLiveData;
    }

    public androidx.lifecycle.MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
