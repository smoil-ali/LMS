package com.example.lms.Listener;

import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.SectionResponse;

import retrofit2.Response;

public interface SectionListener {
    public void onSuccess(Response<SectionResponse> response);
}
