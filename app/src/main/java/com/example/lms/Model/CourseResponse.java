package com.example.lms.Model;

import java.util.ArrayList;
import java.util.List;

public class CourseResponse {
    private String message;

    private String code;

    private String status;

    private List<CourseData> data;

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

    public List<CourseData> getData() {
        return data;
    }

    public void setData(List<CourseData> data) {
        this.data = data;
    }
}
