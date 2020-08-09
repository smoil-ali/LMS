package com.example.lms.Model;

import java.util.List;

public class Category_subCategoryModel {
    CategoryData categoryData;
    List<CategoryData> list;

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public List<CategoryData> getList() {
        return list;
    }

    public void setList(List<CategoryData> list) {
        this.list = list;
    }
}
