package com.example.lms.Model;

public class LoginResponse {
    String message;
    String code;
    String status;
    data data;

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

    public com.example.lms.Model.data getData() {
        return data;
    }

    public void setData(com.example.lms.Model.data data) {
        this.data = data;
    }
}
