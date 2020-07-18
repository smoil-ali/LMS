package com.example.lms.Model;

public class EnrollmentHistoryResponse {
    String message;
    String code;
    String status;
    EnrollmentHistoryData data;

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

    public EnrollmentHistoryData getData() {
        return data;
    }

    public void setData(EnrollmentHistoryData data) {
        this.data = data;
    }
}
