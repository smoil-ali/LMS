package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseData;
import com.example.lms.Repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseViewModel extends ViewModel implements CourseListener {
    public MutableLiveData<List<CourseData>> arrayListMutableLiveData;
    public MutableLiveData<CourseCount> courseCountMutableLiveData;
    CourseRepository courseRepository;
    String TAG = CourseViewModel.class.getSimpleName();

    public CourseViewModel(Context context, ProgressBar progressBar) {
        arrayListMutableLiveData = new MutableLiveData<>();
        courseCountMutableLiveData = new MutableLiveData<>();
        courseRepository = new CourseRepository(context,progressBar);
        courseRepository.getCountCourse();
        courseRepository.setCourseListener(this);
    }

    public MutableLiveData<List<CourseData>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    @Override
    public void courseListener(List<CourseData> courseData, String msg) {
        Log.i(TAG, String.valueOf(courseData.size()));
        arrayListMutableLiveData.setValue(courseData);
    }

    @Override
    public void errorListener(String error) {
        Log.i(TAG,error);
    }

    @Override
    public void courseCountListener(CourseCount courseCount) {
        Log.i(TAG,courseCount.getActive()+""+courseCount.getFree()+""+
                courseCount.getPaid()+""+courseCount.getPending());
        courseCountMutableLiveData.setValue(courseCount);
    }

    @Override
    public void courseCounterror(String error) {
        Log.i(TAG,error);
    }

    public MutableLiveData<CourseCount> getCourseCountMutableLiveData() {
        return courseCountMutableLiveData;
    }
}
