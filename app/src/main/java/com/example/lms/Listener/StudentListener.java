package com.example.lms.Listener;

import com.example.lms.Model.StudentData;

import java.util.List;

public interface StudentListener {
    public void onSuccess(List<StudentData> studentDataList);
    public void onError(String error);
}
