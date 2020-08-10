package com.example.lms.Model;

import java.io.Serializable;
import java.util.List;

public class Lesson implements Serializable {
    private int count;

    private List<LessonData> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LessonData> getData() {
        return data;
    }

    public void setData(List<LessonData> data) {
        this.data = data;
    }
}
