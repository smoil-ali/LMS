package com.example.lms.ViewModels;

import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.WebsiteSettingsListener;
import com.example.lms.Model.WebsiteSettingData;
import com.example.lms.Repository.WebsiteSettingsRepository;

public class WebSettingsViewModel extends ViewModel implements WebsiteSettingsListener {
    public MutableLiveData<WebsiteSettingData> mutableLiveData;
    public MutableLiveData<String> errorMessage;

    WebsiteSettingsRepository websiteSettingsRepository;
    String TAG=InstructorViewModel.class.getSimpleName();

    public WebSettingsViewModel(ProgressBar progressBar){
        this.mutableLiveData=new MutableLiveData<>();
        this.errorMessage=new MutableLiveData<>();
        websiteSettingsRepository=new WebsiteSettingsRepository(progressBar);
        websiteSettingsRepository.setListener(this);
    }

    public void update(ProgressBar progressBar){
        this.mutableLiveData=new MutableLiveData<>();
        this.errorMessage=new MutableLiveData<>();
        websiteSettingsRepository=new WebsiteSettingsRepository(progressBar);
        websiteSettingsRepository.setListener(this);
    }

    public MutableLiveData<WebsiteSettingData> getwebMutableLiveData(){
        return mutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void websiteSettingsListener(WebsiteSettingData websiteSettingData) {
        mutableLiveData.setValue(websiteSettingData);
    }

    @Override
    public void websiteSettingsError(String error) {
        errorMessage.setValue(error);
    }
}
