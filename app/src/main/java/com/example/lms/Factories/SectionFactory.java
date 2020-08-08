package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.ProfileViewModel;
import com.example.lms.ViewModels.SectionViewModel;

public class SectionFactory implements ViewModelProvider.Factory {
    Context context;
    ProgressBar progressBar;
    String id;

    public SectionFactory(Context context, ProgressBar progressBar, String id) {
        this.context = context;
        this.progressBar = progressBar;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SectionViewModel(context,progressBar,id);
    }
}
