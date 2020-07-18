package com.example.lms.Listener;

import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollmentHistoryData;

import java.util.List;

public interface EnrollListener {
    public void enrollListener(EnrollmentHistoryData enrollmentHistoryData, String msg);
    public void errorListener(String error);
}
