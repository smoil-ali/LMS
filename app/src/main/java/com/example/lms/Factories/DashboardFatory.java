package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.DashboardViewModel;

public class DashboardFatory implements ViewModelProvider.Factory {
    ProgressBar progressBar;
    Context context;

    public DashboardFatory(ProgressBar progressBar, Context context) {
        this.progressBar = progressBar;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DashboardViewModel(context,progressBar);
    }
}
