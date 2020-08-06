package com.example.lms.Listener;

import com.example.lms.Model.InstructorResponse;

import retrofit2.Response;

public interface InstructorListener {
    public void onSuccess(Response<InstructorResponse> response);
}
