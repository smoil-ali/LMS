package com.example.lms.Model;

import java.util.List;

public class PayoutResponse {
    private String message;

    private String status;

    private String code;

    private List<InstructorPayout> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InstructorPayout> getData() {
        return data;
    }

    public void setData(List<InstructorPayout> data) {
        this.data = data;
    }
}
