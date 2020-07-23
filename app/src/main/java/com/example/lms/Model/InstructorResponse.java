package com.example.lms.Model;

import java.util.List;

public class InstructorResponse {
    String message;
    String code;
    String status;
    List<InstructorData> data;

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

    public List<InstructorData> getData() {
        return data;
    }

    public void setData(List<InstructorData> data) {
        this.data = data;
    }
}
