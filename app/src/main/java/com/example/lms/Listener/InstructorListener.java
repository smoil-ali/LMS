package com.example.lms.Listener;

import com.example.lms.Model.InstructorData;
import com.example.lms.Model.StudentData;

import java.util.List;

public interface InstructorListener {
    public void onSuccess(List<InstructorData> instructorData);
    public void onError(String error);
}
