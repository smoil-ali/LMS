package com.example.lms.Listener;

import com.example.lms.Model.LessonData;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.data;

import java.util.List;

public interface LessonListener {
    public void onSuccess(List<LessonData> lessonData, SectionData sectionData);
}
