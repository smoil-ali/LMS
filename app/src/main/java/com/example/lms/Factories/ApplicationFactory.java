package com.example.lms.Factories;

import android.text.Editable;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.ApplicationViewModel;

public class ApplicationFactory implements ViewModelProvider.Factory {

    ProgressBar progressBar;
    int status;

    public ApplicationFactory(ProgressBar progressBar,int status) {
        this.progressBar = progressBar;this.status=status;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ApplicationViewModel(progressBar,status);
    }
}

