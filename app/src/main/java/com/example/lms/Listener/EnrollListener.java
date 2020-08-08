package com.example.lms.Listener;

import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.NetworkState;

import java.util.List;

import retrofit2.Response;

public interface EnrollListener {
    public void enrollListener(Response<EnrollmentHistoryResponse> response);
}
