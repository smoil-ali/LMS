package com.example.lms.ViewModels;

import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.ApplicationListener;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.Repository.ApprovedApplicationRepository;

import java.util.List;

public class ApplicationViewModel extends ViewModel implements ApplicationListener {
    public MutableLiveData<List<ApprovedApplication>> applicationMutableLiveData;
    public MutableLiveData<String> errorMessage;
    ApprovedApplicationRepository approvedApplicationRepository;
    String Tag = ApplicationViewModel.class.getSimpleName();

    public ApplicationViewModel(ProgressBar progressBar,int status) {
        applicationMutableLiveData=new MutableLiveData<>();
        errorMessage=new MutableLiveData<>();
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
