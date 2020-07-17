package com.example.lms.Model;

import java.util.List;

public class Enrollment {
    private int count;

    private List<EnrollmentData> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EnrollmentData> getData() {
        return data;
    }

    public void setData(List<EnrollmentData> data) {
        this.data = data;
    }
}
