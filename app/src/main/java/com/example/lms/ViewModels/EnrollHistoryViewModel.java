package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Repository.CourseRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;

import java.util.List;

public class EnrollHistoryViewModel extends ViewModel implements EnrollListener {
    public MutableLiveData<List<EnrollHistoryUserData>> historyDataMutableLiveData;
    public MutableLiveData<String> errorMessage ;
    EnrollmentHistoryRepository enrollmentHistoryRepository;
    String TAG = EnrollHistoryViewModel.class.getSimpleName();

    public EnrollHistoryViewModel(Context context, ProgressBar progressBar, String from,String to) {
        historyDataMutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        enrollmentHistoryRepository = new EnrollmentHistoryRepository(context,progressBar,from,to);
        enrollmentHistoryRepository.setEnrollListener(this);
    }

    public void update(Context context,ProgressBar progressBar,String from,String to){
        enrollmentHistoryRepository = new EnrollmentHistoryRepository(context,progressBar,from,to);
        enrollmentHistoryRepository.setEnrollListener(this);
    }


    @Override
    public void enrollListener(List<EnrollHistoryUserData> enrollmentHistoryData, String msg) {
        Log.i(TAG,enrollmentHistoryData.size()+"");
        historyDataMutableLiveData.setValue(enrollmentHistoryData);
    }

    @Override
    public void errorListener(String error) {
        Log.i(TAG,""+error);
        errorMessage.setValue(error);
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<List<EnrollHistoryUserData>> getHistoryDataMutableLiveData() {
        return historyDataMutableLiveData;
    }
}
