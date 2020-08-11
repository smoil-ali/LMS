package com.example.lms.ViewModels;

import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.ApplicationListener;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.Repository.ApprovedApplicationRepository;

import java.util.List;

public class PendingApplicationViewModel extends ViewModel implements ApplicationListener {
    MutableLiveData<List<ApprovedApplication>> applicationMutableLiveData;
    MutableLiveData<String> errorMessage;
    ApprovedApplicationRepository approvedApplicationRepository;
    String Tag = PendingApplicationViewModel.class.getSimpleName();

    public PendingApplicationViewModel(ProgressBar progressBar, int status) {
        applicationMutableLiveData=new MutableLiveData<>();
        errorMessage=new MutableLiveData<>();
        approvedApplicationRepository=new ApprovedApplicationRepository(progressBar,status);
        approvedApplicationRepository.setApplicationListener(this);
    }


    public void update(ProgressBar progressBar,int status){
        approvedApplicationRepository=new ApprovedApplicationRepository(progressBar,status);
        approvedApplicationRepository.setApplicationListener(this);
    }

    @Override
    public void applicationListner(List<ApprovedApplication> approvedApplications, String msg) {
        applicationMutableLiveData.setValue(approvedApplications);

    }

    @Override
    public void applicationError(String error) {
        applicationError(error);
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<List<ApprovedApplication>> getApplicationMutableLiveData() {
        return applicationMutableLiveData;
    }
}
