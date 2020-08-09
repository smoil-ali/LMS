package com.example.lms.Listener;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Category_subCategoryModel;

import java.util.List;

import retrofit2.Response;

public interface CategoryListener {
    public void onCategoryList(List<Category_subCategoryModel> list);
}
