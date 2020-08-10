package com.example.lms.Model;

import java.util.List;

public class WebsiteResponse {
    private String message;

    private String status;

    private String code;

    private WebsiteSettingData data;

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

    public WebsiteSettingData getData() {
        return data;
    }

    public void setData(WebsiteSettingData data) {
        this.data = data;
    }
}
