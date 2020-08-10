package com.example.lms.ViewModels;

import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.SettingsListener;
import com.example.lms.Model.SettingsData;
import com.example.lms.Repository.SettingsRepository;

public class SettingsViewModel extends ViewModel implements SettingsListener {
    public MutableLiveData<SettingsData> mutableLiveData;
    public MutableLiveData<String> errorMessage;

    SettingsRepository settingsRepository;
    String TAG=SettingsViewModel.class.getSimpleName();
    public SettingsViewModel(ProgressBar progressBar){
        this.mutableLiveData=new MutableLiveData<>();
        this.errorMessage=new MutableLiveData<>();
        settingsRepository=new SettingsRepository(progressBar);
        settingsRepository.setSettingsListener(this);
    }

    public void udpateData(ProgressBar progressBar){
        this.mutableLiveData=new MutableLiveData<>();
        this.errorMessage=new MutableLiveData<>();
        settingsRepository=new SettingsRepository(progressBar);
        settingsRepository.setSettingsListener(this);
    }

    public MutableLiveData<SettingsData> getSettingsMutableData(){
        return mutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage(){
        return errorMessage;
    }
    @Override
    public void settingsDataListener(SettingsData data) {
        mutableLiveData.setValue(data);
    }

    @Override
    public void settingsError(String error) {
        errorMessage.setValue(error);
    }
}
