package com.example.lms.Factories;

import android.text.Editable;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.SettingsViewModel;

public class SettingsFactory implements ViewModelProvider.Factory {
    ProgressBar progressBar;
    public SettingsFactory(ProgressBar progressBar){this.progressBar=progressBar;}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(progressBar);
    }
}
