package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Repository.CategoryRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;
import com.example.lms.Repository.RxjavaReposit;

import java.util.List;

import retrofit2.Response;

public class CategoryViewModel extends ViewModel implements CategoryListener {
    MutableLiveData<Response<CategoryResponse>> categoryLiveData;
    CategoryRepository categoryRepository;
    String TAG = CategoryViewModel.class.getSimpleName();

    public CategoryViewModel(Context context,ProgressBar progressBar) {
        categoryLiveData = new MutableLiveData<>();
        categoryRepository = new CategoryRepository(context,progressBar);
        categoryRepository.setCategoryListener(this);
    }

    public void update(Context context,ProgressBar progressBar){
        categoryRepository = new CategoryRepository(context,progressBar);
        categoryRepository.setCategoryListener(this);
    }

    @Override
    public void onCategoryList(Response<CategoryResponse> response) {
        categoryLiveData.setValue(response);
    }

    public MutableLiveData<Response<CategoryResponse>> getCategoryLiveData() {
        return categoryLiveData;
    }
}
