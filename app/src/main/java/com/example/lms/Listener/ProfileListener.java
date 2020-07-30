package com.example.lms.Listener;

import com.example.lms.Model.StudentData;
import com.example.lms.Model.data;

import java.util.List;

public interface ProfileListener {
    public void onSuccess(data data);
    public void onError(String error);
}
