package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.DashboardListner;
import com.example.lms.Model.DashboardCount;
import com.example.lms.Repository.DashboardRepository;

public class DashboardViewModel extends ViewModel implements DashboardListner {
    public MutableLiveData<DashboardCount> dashboardCountMutableLiveData;
    String TAG=DashboardViewModel.class.getSimpleName();
    DashboardRepository dashboardRepository=new DashboardRepository();
    public DashboardViewModel(Context context, ProgressBar progressBar){
        dashboardCountMutableLiveData=new MutableLiveData<>();
        dashboardRepository.getDashboardCount(progressBar);
        dashboardRepository.setDashboardListner(this);
    }

    @Override
    public void dashboardCountListner(DashboardCount dashboardCount) {

        Log.i(TAG,dashboardCount.getCourse()+" "+dashboardCount.getEnrollment()+" "+dashboardCount.getLesson()
        +" "+dashboardCount.getStudent());
        dashboardCountMutableLiveData.setValue(dashboardCount);
    }

    @Override
    public void dashboardCountError(String error) { Log.i(TAG,error); }

    public MutableLiveData<DashboardCount> getDashboardCountMutableLiveData(){
        return dashboardCountMutableLiveData;
    }
}
