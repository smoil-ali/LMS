package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Repository.CourseRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;

import java.util.List;

public class EnrollHistoryViewModel extends ViewModel implements EnrollListener {
    public MutableLiveData<EnrollmentHistoryData> historyDataMutableLiveData;
    EnrollmentHistoryRepository enrollmentHistoryRepository;
    String TAG = EnrollHistoryViewModel.class.getSimpleName();

    public EnrollHistoryViewModel(Context context, ProgressBar progressBar) {
        historyDataMutableLiveData = new MutableLiveData<>();
        enrollmentHistoryRepository = new EnrollmentHistoryRepository(context,progressBar);
        enrollmentHistoryRepository.setEnrollListener(this);
    }

    @Override
    public void enrollListener(EnrollmentHistoryData enrollmentHistoryData, String msg) {
        historyDataMutableLiveData.setValue(enrollmentHistoryData);
    }

    @Override
    public void errorListener(String error) {
        Log.i(TAG,""+error);
    }

    public MutableLiveData<EnrollmentHistoryData> getHistoryDataMutableLiveData() {
        return historyDataMutableLiveData;
    }
}
