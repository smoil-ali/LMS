package com.example.lms.Model;

import com.example.lms.activity.AddCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static List<String> listOfLevel = new ArrayList(Arrays.asList("Beginner", "Advanced", "Intermediate"));
    public static List<String> listOfLanguages = new ArrayList(Arrays.asList("English"));
    public static List<String> listOfProvider  = new ArrayList(Arrays.asList("YouTube","Vimeo","Html5"));
    public static List<String> listOfLessonsType= new ArrayList(Arrays.asList("YouTube Video","Vimeo Video","Video file",
            "Video url[.mp4]","Document","Image file","Iframe embed"));
    public static List<Section_LessonModel> sectionData ;
    public static String COURSE_ID;
    public static final String FROM = "from";
    public static final String INSTRUCTOR = "instructor";
    public static final String STUDENT = "student";
    public static final String FAILED = "FAILED";
    public static final String RESPONSE_FAILED = "RESPONSE_FAILED";
    public static final String SUCCESS = "success";
    public static final String ACTION = "action";
    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DATA = "data";
    public static final String FIELD_MISSING = "Field Missing";
    public static final String AddCourse= com.example.lms.activity.AddCourse.class.getSimpleName();

}
