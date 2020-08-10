package com.example.lms.Listener;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Sub_category;

import java.util.List;

public interface sub_categoryListener {
    public void onSuccess(List<CategoryData> sub_categories, CategoryData categoryData);
}
