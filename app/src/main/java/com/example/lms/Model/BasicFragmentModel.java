package com.example.lms.Model;

import java.io.Serializable;

public class BasicFragmentModel implements Serializable {
    String courseTitle="";
    String shortDescription="";
    String Description="";
    String category_id="";
    String level;
    String Language;
    String isTopCourse="false";

    public BasicFragmentModel() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getIsTopCourse() {
        return isTopCourse;
    }

    public void setIsTopCourse(String isTopCourse) {
        this.isTopCourse = isTopCourse;
    }
}
