package com.example.lms.Model;

public class EnrollmentHistoryData {
    EnrollmentHistoryData userData;
    String userName;
    String course;

    public EnrollmentHistoryData getUserData() {
        return userData;
    }

    public void setUserData(EnrollmentHistoryData userData) {
        this.userData = userData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
