package com.example.lms.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Section implements Serializable {

    private List<SectionData> data;

    private int count;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<SectionData> getData() {
        return Optional.ofNullable(data).orElse(new ArrayList<>());
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
