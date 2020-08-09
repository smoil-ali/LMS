package com.example.lms.Listener;

import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.UserData;

import java.util.List;

public interface EnrollmentCourseListener {
    public void onSuccess(List<EnrollHistoryUserData> list, UserData userData);
}
