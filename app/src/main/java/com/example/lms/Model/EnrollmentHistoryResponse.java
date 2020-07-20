package com.example.lms.Model;

import java.util.List;

public class EnrollmentHistoryResponse {
    String message;
    String code;
    String status;
    List<EnrollHistoryUserData> data;

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

    public List<EnrollHistoryUserData> getData() {
        return data;
    }

    public void setData(List<EnrollHistoryUserData> data) {
        this.data = data;
    }
}
