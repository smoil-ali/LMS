package com.example.lms.Factories;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Model.WebsiteSettingData;
import com.example.lms.ViewModels.WebSettingsViewModel;

public class WebSettingsFactory implements ViewModelProvider.Factory {
    ProgressBar progressBar;

    public WebSettingsFactory(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WebSettingsViewModel(progressBar);
    }
}
