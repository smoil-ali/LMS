package com.example.lms.Model;

public class DashboardResponse {
    private String message;

    private String status;

    private String code;

    private DashboardCount count;

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

    public DashboardCount getCount() {
        return count;
    }

    public void setCount(DashboardCount count) {
        this.count = count;
    }
}
