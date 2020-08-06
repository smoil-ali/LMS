package com.example.lms.Listener;

import com.example.lms.Model.StudentResponse;

import retrofit2.Response;

public interface StudentListener {
    public void onSuccess(Response<StudentResponse> response);
}
