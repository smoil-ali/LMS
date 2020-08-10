package com.example.lms.Listener;

import com.example.lms.Model.ApprovedApplication;

import java.util.List;

public interface ApplicationListener {
    public void applicationListner(List<ApprovedApplication> approvedApplications,String msg);
    public void applicationError(String error);
}
