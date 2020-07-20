package com.example.lms.Listener;

import com.example.lms.Model.DashboardCount;

public interface DashboardListner {
    public void dashboardCountListner(DashboardCount dashboardCount);
    public void dashboardCountError(String error);
}
