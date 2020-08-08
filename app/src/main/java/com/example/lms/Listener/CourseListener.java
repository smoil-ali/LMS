package com.example.lms.Listener;

import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public interface CourseListener {
    public void courseListener(Response<CourseResponse> response);
    public void courseCountListener(Response<CourseCountResponse> response);
}
