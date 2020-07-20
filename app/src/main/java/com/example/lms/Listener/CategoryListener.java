package com.example.lms.Listener;

import com.example.lms.Model.CategoryData;

import java.util.List;

public interface CategoryListener {
    public void onCategoryList(List<CategoryData> categoryDataList);
    public void onError(String error);
}
