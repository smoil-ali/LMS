package com.example.lms.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.http.PUT;

public class Constants {
    public static List<String> listOfLevel = new ArrayList(Arrays.asList("Beginner", "Advanced", "Intermediate"));
    public static List<String> listOfLanguages = new ArrayList(Arrays.asList("English"));
    public static List<String> listOfProvider  = new ArrayList(Arrays.asList("YouTube","Vimeo","Html5"));
    public static final String FROM = "from";
    public static final String INSTRUCTOR = "instructor";
    public static final String STUDENT = "student";
    public static final String FAILED = "FAILED";
    public static final String RESPONSE_FAILED = "RESPONSE_FAILED";
    public static final String SUCCESS = "success";

}
