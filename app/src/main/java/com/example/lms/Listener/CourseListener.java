package com.example.lms.Listener;

import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseData;

import java.util.ArrayList;
import java.util.List;

public interface CourseListener {
    public void courseListener(List<CourseData> courseData, String msg);
    public void errorListener(String error);
    public void courseCountListener(CourseCount courseCount);
    public void courseCounterror(String error);
}
