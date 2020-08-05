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
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.Error;
import com.example.lms.Model.NetworkState;
import com.example.lms.Repository.CourseRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;

import java.util.List;

import retrofit2.Response;

public class EnrollHistoryViewModel extends ViewModel implements EnrollListener  {
    public MutableLiveData<Response<EnrollmentHistoryResponse>> historyDataMutableLiveData;
    EnrollmentHistoryRepository enrollmentHistoryRepository;
    String TAG = EnrollHistoryViewModel.class.getSimpleName();

    public EnrollHistoryViewModel(Context context, ProgressBar progressBar, String from,String to) {
        enrollmentHistoryRepository = new EnrollmentHistoryRepository(context,progressBar,from,to);
        enrollmentHistoryRepository.setEnrollListener(this);
        historyDataMutableLiveData = new MutableLiveData<>();
    }

    public void update(Context context,ProgressBar progressBar,String from,String to){
        enrollmentHistoryRepository = new EnrollmentHistoryRepository(context,progressBar,from,to);
        enrollmentHistoryRepository.setEnrollListener(this);
    }

    public MutableLiveData<Response<EnrollmentHistoryResponse>> getHistoryDataMutableLiveData() {
        return historyDataMutableLiveData;
    }

    @Override
    public void enrollListener(Response<EnrollmentHistoryResponse> response) {
        historyDataMutableLiveData.setValue(response);
    }
}
