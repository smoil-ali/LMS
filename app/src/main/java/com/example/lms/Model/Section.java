package com.example.lms.Model;

import java.util.List;

public class Section {

    private List<SectionData> data;

    private int count;

    public List<SectionData> getData() {
        return data;
    }

    public void setData(List<SectionData> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
