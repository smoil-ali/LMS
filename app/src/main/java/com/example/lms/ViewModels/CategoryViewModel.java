package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Repository.CategoryRepository;
import com.example.lms.Repository.EnrollmentHistoryRepository;
import com.example.lms.Repository.RxjavaReposit;

import java.util.List;

public class CategoryViewModel extends ViewModel implements CategoryListener {
    public MutableLiveData<List<CategoryData>> categoryLiveData;
    public MutableLiveData<String> errorMessage ;
    CategoryRepository categoryRepository;
    RxjavaReposit rxjavaReposit;
    String TAG = CategoryViewModel.class.getSimpleName();

    public CategoryViewModel(Context context,ProgressBar progressBar) {
        categoryLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        categoryRepository = new CategoryRepository(context,progressBar);
        categoryRepository.setCategoryListener(this);
    }


    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }


    @Override
    public void onCategoryList(List<CategoryData> categoryDataList) {
        categoryLiveData.setValue(categoryDataList);
    }

    @Override
    public void onError(String error) {
        Log.i(TAG,""+error);
        errorMessage.setValue(error);
    }

    public MutableLiveData<List<CategoryData>> getCategoryLiveData() {
        return categoryLiveData;
    }
}
