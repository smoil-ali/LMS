package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.CategoryViewModel;
import com.example.lms.ViewModels.EnrollHistoryViewModel;

public class CategoryFactory implements ViewModelProvider.Factory {
    Context context;
    ProgressBar progressBar;

    public CategoryFactory(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryViewModel(context,progressBar);
    }
}
