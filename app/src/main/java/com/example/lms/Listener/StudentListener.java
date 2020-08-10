package com.example.lms.Listener;

import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.UserData_EnrollmentHistoryModel;

import java.util.List;

import retrofit2.Response;

public interface StudentListener {
    public void onSuccess(List<UserData_EnrollmentHistoryModel> list);
}
