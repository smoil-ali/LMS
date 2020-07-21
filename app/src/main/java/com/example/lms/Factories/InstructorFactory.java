package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.InstructorViewModel;
import com.example.lms.ViewModels.StudentViewModel;

public class InstructorFactory implements ViewModelProvider.Factory{

    Context context;
    ProgressBar progressBar;

    public InstructorFactory(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new InstructorViewModel(context,progressBar);
    }
}
