package com.example.lms.Listener;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;

import java.util.List;

import retrofit2.Response;

public interface CategoryListener {
    public void onCategoryList(Response<CategoryResponse> response);
}
