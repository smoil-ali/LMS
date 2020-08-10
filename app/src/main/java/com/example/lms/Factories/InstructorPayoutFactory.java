package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.PayoutViewModel;

public class InstructorPayoutFactory implements ViewModelProvider.Factory {
    Context context;
    ProgressBar progressBar;

    public InstructorPayoutFactory(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PayoutViewModel(context,progressBar);
    }
}
