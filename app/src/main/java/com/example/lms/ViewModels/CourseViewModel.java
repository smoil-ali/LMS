package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CourseViewModel extends ViewModel implements CourseListener {
    public MutableLiveData<Response<CourseResponse>> arrayListMutableLiveData;
    public MutableLiveData<Response<CourseCountResponse>> courseCountMutableLiveData;
    CourseRepository courseRepository;
    String TAG = CourseViewModel.class.getSimpleName();

    public CourseViewModel(Context context, ProgressBar progressBar) {
        arrayListMutableLiveData = new MutableLiveData<>();
        courseCountMutableLiveData = new MutableLiveData<>();
        courseRepository = new CourseRepository(context,progressBar);
        courseRepository.getCountCourse(context);
        courseRepository.setCourseListener(this);
    }

    public void update(Context context,ProgressBar progressBar){
        courseRepository = new CourseRepository(context,progressBar);
        courseRepository.setCourseListener(this);
    }

    @Override
    public void courseListener(Response<CourseResponse> response) {
        arrayListMutableLiveData.setValue(response);
    }

    @Override
    public void courseCountListener(Response<CourseCountResponse> response) {
        courseCountMutableLiveData.setValue(response);
    }

    public MutableLiveData<Response<CourseResponse>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    public MutableLiveData<Response<CourseCountResponse>> getCourseCountMutableLiveData() {
        return courseCountMutableLiveData;
    }
}
