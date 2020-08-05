package com.example.lms.Factories;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.ViewModels.EnrollHistoryViewModel;

public class EnrollHistoryFactory implements ViewModelProvider.Factory {
    Context context;
    ProgressBar progressBar;
    String from,to;
    public EnrollHistoryFactory(Context context, ProgressBar progressBar,String from,String to) {
        this.context = context;
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EnrollHistoryViewModel(context,progressBar,from,to);
    }
}
