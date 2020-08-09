package com.example.lms.Listener;

import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Section_LessonModel;

import java.util.List;

import retrofit2.Response;

public interface SectionListener {
    public void onSuccess(List<Section_LessonModel> list);
}
