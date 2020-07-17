package com.example.lms.Model;

public class SharedPref {
    private String id;
    private boolean status;

    public SharedPref() {
    }

    public SharedPref(String id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
