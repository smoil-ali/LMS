package com.example.lms.Model;

public class CourseCountResponse {
    String message;
    String code;
    String status;
    CourseCount course_count;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseCount getCourse_count() {
        return course_count;
    }

    public void setCourse_count(CourseCount course_count) {
        this.course_count = course_count;
    }
}
