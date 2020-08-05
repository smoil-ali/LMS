package com.example.lms.Listener;

import com.example.lms.Model.InstructorData;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.StudentData;

import java.util.List;

import retrofit2.Response;

public interface InstructorListener {
    public void onSuccess(Response<InstructorResponse> response);
}
