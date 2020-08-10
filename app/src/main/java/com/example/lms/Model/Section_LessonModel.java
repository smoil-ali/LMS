package com.example.lms.Model;

import java.util.List;

public class Section_LessonModel {
    SectionData sectionData;
    List<LessonData> lessonDataList;

    public SectionData getSectionData() {
        return sectionData;
    }

    public void setSectionData(SectionData sectionData) {
        this.sectionData = sectionData;
    }

    public List<LessonData> getLessonDataList() {
        return lessonDataList;
    }

    public void setLessonDataList(List<LessonData> lessonDataList) {
        this.lessonDataList = lessonDataList;
    }
}
