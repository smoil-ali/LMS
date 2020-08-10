package com.example.lms.Factories;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.ApplicationViewModel;
import com.example.lms.ViewModels.PendingApplicationViewModel;

public class PendingApplicationFactory implements ViewModelProvider.Factory {

    ProgressBar progressBar;
    int status;

    public PendingApplicationFactory(ProgressBar progressBar, int status) {
        this.progressBar = progressBar;this.status=status;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PendingApplicationViewModel(progressBar,status);
    }
}

