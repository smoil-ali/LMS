package com.example.lms.Model;

import java.io.Serializable;
import java.util.List;

public class SectionResponse implements Serializable {
    String message;
    String code;
    String status;
    List<SectionData> data;

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

    public List<SectionData> getData() {
        return data;
    }

    public void setData(List<SectionData> data) {
        this.data = data;
    }
}
